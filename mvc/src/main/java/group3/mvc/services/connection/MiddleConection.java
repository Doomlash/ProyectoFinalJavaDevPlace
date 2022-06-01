package group3.mvc.services.connection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class MiddleConection {
    WebClient webClient;

    public MiddleConection(){
        webClient = WebClient.builder()
                    .baseUrl("http://localhost:8081")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }
    public double getRatio(String code){
        ResponseEntity<Double> response = webClient.get()
        .uri("/gifRatio/"+code)
        .retrieve()
        .toEntity(Double.class)
        .block();
        return response.getBody();
    }
}