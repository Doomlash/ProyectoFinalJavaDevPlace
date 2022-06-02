package group3.mvc.services;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.Message;
import group3.mvc.model.UserHolder;
import group3.mvc.model.request.MessageRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMessages;
import reactor.core.publisher.Mono;

@Service
public class MessagesService implements IMessages{
    private WebClient wCs = Connection.getClient();

    public Integer createMessage(Message ms){
        MessageRequest mr = new MessageRequest(ms.getContent(), ms.getLanguage(), ms.getSenderId(), ms.getReceiverId());
        try {
            ResponseEntity<Integer> rCm = wCs.post()
                    .uri("/messages")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(mr), MessageRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            updateHolderMS(ms,"crM");
            return rCm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return createMessage(ms);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public List<Message> listAllMessages(){
        try {
            ResponseEntity<Message[]> rLAm = wCs.get()
                    .uri("/messages")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Message[].class)
                    .block();
            List<Message> rLAml = Arrays.asList(rLAm.getBody());
            return rLAml;
        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return listAllMessages();
            }
            return null;
        }
    }

    public Integer receiveMessage(Message ms){
        try{
            ResponseEntity<Integer> rRCm = wCs.put()
                    .uri("/messages/receive/"+ ms.getId())
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            updateHolderMS(ms,"rcM");

            return rRCm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return receiveMessage(ms);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }

    }

    public Integer readMessage (Message ms){
        try{
            ResponseEntity<Integer> rRDm = wCs.put()
                    .uri("/messages/read/"+ ms.getId())
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            updateMessage(ms,"rdM");

            return rRDm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return readMessage(ms);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Message translate(Message message, String langU){
        try {
            ResponseEntity<Message> rTm = wCs.post()
                    .uri("/messages/translate/"+langU)
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(message),Message.class)
                    .retrieve()
                    .toEntity(Message.class)
                    .block();
            updateMessageContent(rTm.getBody());
            return rTm.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return translate(message,langU);
            }
            return null;
        }
    }

    private void updateMessageContent(Message body) {
        for (Message message : UserHolder.getCurrentUser().getReceived()) {
            if(message.getId()==body.getId()){
                message.setContent(body.getContent());
                message.setLanguage(body.getLanguage());
                break;
            }
        }
        for (Message message : UserHolder.getCurrentUser().getSent()) {
            if(message.getId()==body.getId()){
                message.setContent(body.getContent());
                message.setLanguage(body.getLanguage());
                break;
            }
        }
	}

	public void updateHolderMS(Message message, String rta){
        switch (rta){
            case "crM":
                message.setCreationDate(Date.from(Instant.now()));
                UserHolder.getCurrentUser().addMessage(message);
                break;
            case "rdM":
                updateMessage(message,"rdM");
                break;
            case "rcM":
                updateMessage(message,"rcM");
                break;
            case "tM":
                /*actualizar holder? necesario?*/
                break;
        }
    }

    public void updateMessage(Message mess,String rta){
        for(Message message : UserHolder.getCurrentUser().getReceived()){
            if (message.getId() == mess.getId()) {
                if(rta.compareTo("rdM") ==0) {
                    message.setReceptionDate(Date.from(Instant.now()));
                    break;
                }else {
                    message.setReadDate(Date.from(Instant.now()));
                    break;
                }
            }
        }
    }

    @Override
    public List<Message> filterMessagesContact(Long id) {
        List<Message> response = new LinkedList<>();
        for (Message message : UserHolder.getCurrentUser().getSent()) {
            if(message.getReceiverId()==id){
                response.add(message);
            }
        }
        for (Message message : UserHolder.getCurrentUser().getReceived()) {
            if(message.getSenderId()==id){
                response.add(message);
                if(message.getReadDate()!=null){
                    readMessage(message);
                }
            }
        }
        Collections.sort(response);
        return response;
    }
    @Override
    public List<Message> filterMessagesGroup(Long id) {
        List<Message> response = new LinkedList<>();
        for (Message message : UserHolder.getCurrentUser().getSent()) {
            if(message.getReceiverId()==id){
                response.add(message);
            }
        }
        for (Message message : UserHolder.getCurrentUser().getReceived()) {
            if(message.getReceiverId()==id){
                response.add(message);
            }
        }
        Collections.sort(response);
        return response;
    }

	@Override
	public Message getMessage(Long messageId) {
        for (Message message : UserHolder.getCurrentUser().getReceived()) {
            if(message.getId()==messageId){
                return message;
            }
        }
        for (Message message : UserHolder.getCurrentUser().getSent()) {
            if(message.getId()==messageId){
                return message;
            }
        }
        return null;
	}


}
