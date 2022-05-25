package group3.middleware.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group3.middleware.model.Messages;
import group3.middleware.services.connection.Connection;
import group3.middleware.services.implementation.IMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MessagesService implements IMessages{
    private ObjectMapper mapper = new ObjectMapper();
    private WebClient wCs = new Connection('m').getClient();
    private WebClient wCT = new Connection('t').getClient();


    public int create(Long idU, Messages m){
        ResponseEntity<String> response = wCs.post()
                .uri("/"+ idU)
                .body(m,Messages.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public List<Messages> messagesByContact(Long idU,Long idC){
        Flux<Messages> messagesF = wCs.get()
                .uri("/" + idU + "/" + idC)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> messageByGroup(Long idU,Long idG){
        Flux<Messages> messagesF = wCs.get()
                .uri("/" + idG)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> getAll(Long idU){
        Flux<Messages> messagesF = wCs.get()
                .uri("/" + idU)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public int delete(Long idM){
        ResponseEntity<String> response = wCs.delete()
                .uri("/"+ idM)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public Messages translate(Messages m){
        ResponseEntity<String> response = wCT.get()
                .uri("/?lang=" + m.getLanguage() + "&text=" + m.getContent())
                .header("X-RapidAPI-Host", "just-translated.p.rapidapi.com")
                .header("X-RapidAPI-Key", "3461aac04fmsh4c595f383655f5fp170ef8jsnb5b197c8a5f0")
                .retrieve().toEntity(String.class).block();
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

    /*
    public List<Messages> messagesSentByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = wCs.get()
                .uri("/sent/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> messagesReceivedByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = wCs.get()
                .uri("/received/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }*/
}
