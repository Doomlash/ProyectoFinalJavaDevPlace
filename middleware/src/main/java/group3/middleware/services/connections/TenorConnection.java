package group3.middleware.services.connections;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class TenorConnection {
    WebClient webClient;
    private static final ObjectMapper mapper = new ObjectMapper();

    public TenorConnection(){
        webClient = WebClient.builder()
                    .baseUrl("https://g.tenor.com/v1")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }
    public double get(String code){
        ResponseEntity<String> response = webClient.get()
        .uri("/gifs?ids="+code+"&key=8KUGT0Y61SR2&media_filter=minimal")
        .retrieve()
        .toEntity(String.class)
        .block();
        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            rootNode = rootNode.get("results").get(0).get("media").get(0).get("mp4");
            double width = rootNode.get("dims").get(0).asDouble();
            double height = rootNode.get("dims").get(1).asDouble();
            return width/height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
