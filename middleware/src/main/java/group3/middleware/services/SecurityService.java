package group3.middleware.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.middleware.model.LoginResponse;
import group3.middleware.model.request.RegisterRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.services.connection.SecurityConnection;
import reactor.core.publisher.Mono;

@Service
public class SecurityService {

    private WebClient webClient = new Connection('a').getClient();


    public ResponseEntity<Integer> register(RegisterRequest request) {
        try{
            ResponseEntity<Integer> rCu = webClient.post()
                    .uri("/security/register")
                    .body(Mono.just(request),RegisterRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCu;
        }catch (WebClientResponseException e){
            return new ResponseEntity<>(Integer.parseInt(e.getResponseBodyAsString()),e.getStatusCode());
        }
    }


    public Object login(String username) {
        try{
            ResponseEntity<LoginResponse> rCu = webClient.post()
                    .uri("/security/login/"+username)
                    .header("Authorization", SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(LoginResponse.class)
                    .block();
            return rCu.getBody();
        }catch (WebClientResponseException e){
            return new ResponseEntity<>(Integer.parseInt(e.getResponseBodyAsString()),e.getStatusCode());
        }
    }


    public Object getToken() {
        try {
            ResponseEntity<String> rCu = webClient.post()
                    .uri("/security/token")
                    .header("Authorization", SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            return rCu.getBody();
        } catch (WebClientResponseException e) {
            return new ResponseEntity<>(Integer.parseInt(e.getResponseBodyAsString()),e.getStatusCode());
        }
    }


    public Object getCredentials(String username) {
        try {
            
            String response = webClient.get()
                    .uri("/security/credentials/"+username)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            throw new UsernameNotFoundException("UsernameNotFound");     
        }
    }
    
}
