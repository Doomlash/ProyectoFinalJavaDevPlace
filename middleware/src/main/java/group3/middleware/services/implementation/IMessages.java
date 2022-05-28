package group3.middleware.services.implementation;

import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import org.springframework.http.ResponseEntity;


public interface IMessages {
     public ResponseEntity<Message> createMessage(MessageRequest message);
     public ResponseEntity<Message[]> listAllMessages();
     public ResponseEntity<String> receiveMessage(Long id);
     public ResponseEntity<String> readMessage(Long id);

     public Message translate(Message m);
}
