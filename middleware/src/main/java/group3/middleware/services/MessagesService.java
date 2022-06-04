package group3.middleware.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import group3.middleware.services.connection.ApiConnection;
import group3.middleware.services.connection.SecurityConnection;
import group3.middleware.services.connection.TranslationConection;
import group3.middleware.services.interfaces.IMessages;
import reactor.core.publisher.Mono;

@Service
public class MessagesService implements IMessages{
    private ObjectMapper mapper = new ObjectMapper();
    private WebClient wCs = new ApiConnection('m').getClient();
    private WebClient wCT = new ApiConnection('t').getClient();
    private TranslationConection translationConection = new TranslationConection();


    public ResponseEntity<Integer> createMessage(MessageRequest mr){
        try {
            ResponseEntity<Integer> rCm = wCs.post()
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(mr), MessageRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCm;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Message[]> listAllMessages(){
        try{
            ResponseEntity<Message[]> rLAm = wCs.get()
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Message[].class)
                    .block();
            return rLAm;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
                return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Integer> receiveMessage(Long idM){
        try {
            ResponseEntity<Integer> rRCm = wCs.put()
                    .uri("/receive/"+ idM)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRCm;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRDm;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    @Override
    public Message translate(Message m, String lanU) {
        m.setContent(translationConection.translate(m.getLanguage(), lanU, m.getContent()));
        m.setLanguage("en");
        return m;
    }
    



////////////////////////////////////////////////////////////////////////////////////

    // public Message translate(Message m,String lanU){
    //     System.out.println(m.toString());
    //     ResponseEntity<String> response = wCT.get()
    //             .uri("/?lang=" + lanU + "&text=" + m.getContent())
    //             .header("X-RapidAPI-Host", "just-translated.p.rapidapi.com")
    //             .header("X-RapidAPI-Key", "3461aac04fmsh4c595f383655f5fp170ef8jsnb5b197c8a5f0")
    //             .retrieve()
    //             .toEntity(String.class)
    //             .block();
    //     m.setContent(response.getBody());
    //     m.setLanguage(lanU);
        
    //     try {
    //         JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
    //         rootNode = rootNode.get("text").get(0);
    //         m.setContent(rootNode.asText());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return m;
    // }

     // public Message translate(Message m,String lanU){
    //     System.out.println(m.toString());
    //     ResponseEntity<String> response = wCT.get()
    //             .uri("/?lang=" + lanU + "&text=" + m.getContent())
    //             .header("X-RapidAPI-Host", "just-translated.p.rapidapi.com")
    //             .header("X-RapidAPI-Key", "3461aac04fmsh4c595f383655f5fp170ef8jsnb5b197c8a5f0")
    //             .retrieve()
    //             .toEntity(String.class)
    //             .block();
    //     m.setContent(response.getBody());
    //     m.setLanguage(lanU);
        
    //     try {
    //         JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
    //         rootNode = rootNode.get("text").get(0);
    //         m.setContent(rootNode.asText());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return m;
    // }


}
