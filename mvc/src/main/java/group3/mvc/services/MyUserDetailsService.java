package group3.mvc.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.request.RegisterRequest;
import group3.mvc.services.connection.Connection;
import reactor.core.publisher.Mono;

public class MyUserDetailsService implements UserDetailsService{ 
    private WebClient webClient = new Connection('a').getClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String response = webClient.get()
            .uri("/security/credentials/"+username)
            .retrieve()
            .bodyToMono(String.class)
            .block();
            JsonNode rootNode = mapper.readValue(response, JsonNode.class);
            List<SimpleGrantedAuthority> l = new ArrayList<>();
            l.add(new SimpleGrantedAuthority("USER"));
            return new User(username, rootNode.get("password").asText(), l);
        } catch (WebClientResponseException e) {
            throw new UsernameNotFoundException("Username not found");
        } catch (Exception e){
            throw new InternalError("error en conversion JSON");
        }
    }

    public Integer createUser(RegisterRequest request) {
        try {
            ResponseEntity<Integer> response = webClient.post()
            .uri("/security/register")
            .body(Mono.just(request),RegisterRequest.class)
            .retrieve()
            .toEntity(Integer.class)
            .block();
            return 0;
        } catch (WebClientResponseException e) {
           return Integer.valueOf(e.getResponseBodyAsString());
        }

    }
    
}
