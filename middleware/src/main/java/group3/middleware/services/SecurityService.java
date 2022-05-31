package group3.middleware.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.middleware.model.request.RegisterRequest;
import group3.middleware.services.connection.Connection;
import reactor.core.publisher.Mono;

@Service
public class SecurityService {

    private WebClient webClient = new Connection('a').getClient();


    public ResponseEntity<Integer> register(RegisterRequest request) {
        try{
            ResponseEntity<Integer> rCu = webClient.post()
                    .uri("/token/register")
                    .body(Mono.just(request),RegisterRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCu;
        }catch (WebClientResponseException e){
            return new ResponseEntity<>(Integer.parseInt(e.getResponseBodyAsString()),e.getStatusCode());
        }
    }
    
}
