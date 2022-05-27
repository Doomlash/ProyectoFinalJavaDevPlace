package group3.mvc.services.implementation;

import group3.mvc.model.Message;
import group3.mvc.model.MessageRequest;
import org.springframework.http.ResponseEntity;


public interface IMessages {
     public ResponseEntity<Message> createMessage(MessageRequest message);
     public ResponseEntity<Message[]> listAllMessages();
     public ResponseEntity<String> receiveMessage(Long id);
     public ResponseEntity<String> readMessage(Long id);

     public ResponseEntity<Message> translate(Message m);
}
