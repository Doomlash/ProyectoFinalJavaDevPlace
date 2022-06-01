package group3.mvc.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.services.connection.Connection;

public class MyUserDetailsService implements UserDetailsService{ 
    private WebClient webClient = new Connection('a').getClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String response = webClient.post()
            .uri("/security/credentials")
            .retrieve()
            .bodyToMono(String.class)
            .block();
            JsonNode rootNode = mapper.readValue(response, JsonNode.class);
            List<SimpleGrantedAuthority> l = new ArrayList<>();
            l.add(new SimpleGrantedAuthority("USER"));
            System.out.println(username);
            System.out.println(rootNode.get("password").asText());
            return new User(username, rootNode.get("password").asText(), l);
        } catch (WebClientResponseException e) {
            throw new UsernameNotFoundException("Username not found");
        } catch (Exception e){
            throw new InternalError("error en conversion JSON");
        }
    }
    
}
