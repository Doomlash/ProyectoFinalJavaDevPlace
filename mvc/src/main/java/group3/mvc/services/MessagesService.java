package group3.mvc.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group3.mvc.model.Message;
import group3.mvc.model.MessageRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MessagesService implements IMessages{
    private WebClient wCs = new Connection('m').getClient();

    public ResponseEntity<Message> createMessage(MessageRequest mr){
        ResponseEntity<Message> rCm = wCs.post()
                .body(Mono.just(mr), MessageRequest.class)
                .retrieve()
                .toEntity(Message.class)
                .block();
        return rCm;
    }

    public ResponseEntity<Message[]> listAllMessages(){
        ResponseEntity<Message[]> rLAm = wCs.get()
                .retrieve()
                .toEntity(Message[].class)
                .block();
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

    public ResponseEntity<Message> translate(Message m) {
        ResponseEntity<Message> rT = wCs.post()
                .uri("/translate")
                .body(m,Message.class)
                .retrieve()
                .toEntity(Message.class)
                .block();
    return rT;
    }
}
