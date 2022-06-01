package group3.mvc.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import group3.mvc.model.LoginResponse;
import group3.mvc.model.UserHolder;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.connection.MiddleConection;

@Service
public class MiddleService {

    MiddleConection mc = new MiddleConection();

    private WebClient webClient = new Connection('a').getClient();

    public Object getRatio(String id) {
        return mc.getRatio(id);
    }

    public void login() {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        LoginResponse response = webClient.post()
                    .uri("/security/login/"+username)
                    .headers(headers -> headers.setBasicAuth("admin", "admin_otaku"))
                    .retrieve()
                    .bodyToMono(LoginResponse.class)
                    .block();
        UserHolder.setCurrentUser(response.getUser());
        Connection.setToken(response.getToken());
        
    }

    public void register() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginResponse response = webClient.post()
                    .uri("/security/login/"+username)
                    .headers(headers -> headers.setBasicAuth("admin", "admin_otaku"))
                    .retrieve()
                    .bodyToMono(LoginResponse.class)
                    .block();
        UserHolder.setCurrentUser(response.getUser());
        Connection.setToken(response.getToken());
    }
    
}