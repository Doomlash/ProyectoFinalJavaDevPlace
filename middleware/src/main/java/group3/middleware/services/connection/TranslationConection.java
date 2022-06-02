package group3.middleware.services.connection;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TranslationConection {
    private WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public TranslationConection(){
        webClient = WebClient.builder()
                    .baseUrl("https://google-translate1.p.rapidapi.com/language/translate/v2")
                    .defaultHeader("content-type", "application/x-www-form-urlencoded")
                    .defaultHeader("Accept-Encoding", "application/gzip")
                    .defaultHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                    .defaultHeader("X-RapidAPI-Key", "5660a554e9mshcb0159e60eccc2bp14ed46jsn4235848679b3")
                    .build();
    }
    public String translate(String source, String target, String text){
        try {    
            String request = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
            String response = webClient.post()
                                .bodyValue("source="+source+"&target="+target+"&q="+request)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        
            System.out.println(response);
            JsonNode node = mapper.readValue(response, JsonNode.class);
            node = node.get("data").get("translations").get(0).get("translatedText");
            return node.asText();
        } catch (Exception e) {
            return null;
        }
    }
}
