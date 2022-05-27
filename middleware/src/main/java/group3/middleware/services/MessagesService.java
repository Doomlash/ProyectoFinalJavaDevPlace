package group3.middleware.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group3.middleware.model.Group;
import group3.middleware.model.Message;
import group3.middleware.model.MessageRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.services.implementation.IMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessagesService implements IMessages{
    private ObjectMapper mapper = new ObjectMapper();
    private WebClient wCs = new Connection('m').getClient();
    private WebClient wCT = new Connection('t').getClient();


    public ResponseEntity<Message> createMessage(MessageRequest mr){
        ResponseEntity<Message> rCm = wCs.post()
                .body(Mono.just(mr), MessageRequest.class)
                .retrieve()
                .toEntity(Message.class)
                .block();
        return rCm;
    }

    public ResponseEntity<List<Message>> listAllMessages(){
        Flux<Message> fm = wCs.get()
                .retrieve()
                .bodyToFlux(Message.class);

        List<Message> messages = fm.collectList().block();

        ResponseEntity<String> res = wCs.get()
                .retrieve()
                .toEntity(String.class)
                .block();

        ResponseEntity<List<Message>> rLAm = ResponseEntity.status(res.getStatusCode()).body(messages);

        return rLAm;
    }

    public ResponseEntity<String> receiveMessage(Long idM){
        ResponseEntity<String> rRCm = wCs.put()
                .uri("/receive/"+ idM)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rRCm;
    }

    public ResponseEntity<String> readMessage (Long idM){
        ResponseEntity<String> rRDm = wCs.put()
                .uri("/read/"+ idM)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rRDm;
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
