package group3.mvc.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.Message;
import group3.mvc.model.request.MessageRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMessages;
import reactor.core.publisher.Mono;

@Service
public class MessagesService implements IMessages{
    private WebClient wCs = Connection.getClient();

    public Integer createMessage(MessageRequest mr){
        try {
            ResponseEntity<Integer> rCm = wCs.post()
                    .body(Mono.just(mr), MessageRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public List<Message> listAllMessages(){
        ResponseEntity<Message[]> rLAm = wCs.get()
                .retrieve()
                .toEntity(Message[].class)
                .block();
        List<Message> rLAml = Arrays.asList(rLAm.getBody());
        return rLAml;
    }

    public Integer receiveMessage(Long idM){
        try{
            ResponseEntity<Integer> rRCm = wCs.put()
                    .uri("/receive/"+ idM)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRCm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }

    }

    public Integer readMessage (Long idM){
        try{
            ResponseEntity<Integer> rRDm = wCs.put()
                    .uri("/read/"+ idM)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRDm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Message translate(Message message, String langU){
            ResponseEntity<Message> rTm = wCs.post()
                    .uri("/translate/"+langU)
                    .body(Mono.just(message),Message.class)
                    .retrieve()
                    .toEntity(Message.class)
                    .block();
            return rTm.getBody();
    }
}
