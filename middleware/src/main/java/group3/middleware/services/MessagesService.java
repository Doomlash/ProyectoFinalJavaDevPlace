package group3.middleware.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.services.implementation.IMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class MessagesService implements IMessages{
    private ObjectMapper mapper = new ObjectMapper();
    private WebClient wCs = new Connection('m').getClient();
    private WebClient wCT = new Connection('t').getClient();


    public ResponseEntity<Integer> createMessage(MessageRequest mr){
        try {
            ResponseEntity<Integer> rCm = wCs.post()
                    .body(Mono.just(mr), MessageRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCm;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }

    }

    public ResponseEntity<Message[]> listAllMessages(){
        ResponseEntity<Message[]> rLAm = wCs.get()
                .retrieve()
                .toEntity(Message[].class)
                .block();
        return rLAm;
    }

    public ResponseEntity<Integer> receiveMessage(Long idM){
        try {
            ResponseEntity<Integer> rRCm = wCs.put()
                    .uri("/receive/"+ idM)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRCm;
        }catch (WebClientResponseException e){
            if(e.getResponseBodyAsString() == null){
                return ResponseEntity.status(e.getStatusCode()).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }

    }

    public ResponseEntity<Integer> readMessage (Long idM){
        try {
            ResponseEntity<Integer> rRDm = wCs.put()
                    .uri("/read/"+ idM)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRDm;
        }catch (WebClientResponseException e){
            if(e.getResponseBodyAsString() == null){
                return ResponseEntity.status(e.getStatusCode()).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }


////////////////////////////////////////////////////////////////////////////////////

    public Message translate(Message m){
        ResponseEntity<String> response = wCT.get()
                .uri("/?lang=" + m.getLanguage() + "&text=" + m.getContent())
                .header("X-RapidAPI-Host", "just-translated.p.rapidapi.com")
                .header("X-RapidAPI-Key", "3461aac04fmsh4c595f383655f5fp170ef8jsnb5b197c8a5f0")
                .retrieve()
                .toEntity(String.class)
                .block();
        m.setContent(response.getBody());

        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            rootNode = rootNode.get("text").get(0);
            m.setContent(rootNode.asText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
