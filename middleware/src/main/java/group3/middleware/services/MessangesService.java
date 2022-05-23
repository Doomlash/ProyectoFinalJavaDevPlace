package group3.middleware.services;

import group3.middleware.model.Messages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MessangesService {
    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/messages")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE).build();


    public int createMessage(Long myId, Messages message){
        ResponseEntity<String> response = webClient.post()
                .uri("/"+ myId)
                .body(message,Messages.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }



    public List<Messages> messagesByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = webClient.get()
                .uri("/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> messageByGroup(Long idUser,Long idGroup){
        Flux<Messages> messagesF = webClient.get()
                .uri("/" + idGroup)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> getAllMessages(Long myId){
        Flux<Messages> messagesF = webClient.get()
                .uri("/" + myId)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public int deleteMessage(Long idm){
        ResponseEntity<String> response = webClient.delete()
                .uri("/"+ idm)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    /*
    public List<Messages> messagesSentByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = webClient.get()
                .uri("/sent/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> messagesReceivedByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = webClient.get()
                .uri("/received/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }*/
}
