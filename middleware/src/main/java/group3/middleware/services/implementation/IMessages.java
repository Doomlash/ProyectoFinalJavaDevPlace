package group3.middleware.services.implementation;

import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import org.springframework.http.ResponseEntity;


public interface IMessages {
     public ResponseEntity<Integer> createMessage(MessageRequest message);
     public ResponseEntity<Message[]> listAllMessages();
     public ResponseEntity<Integer> receiveMessage(Long id);
     public ResponseEntity<Integer> readMessage(Long id);

     public Message translate(Message m);
}
