package group3.middleware.services.implementation;

import group3.middleware.model.Message;
import group3.middleware.model.MessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMessages {
     public ResponseEntity<Message> createMessage(MessageRequest message);
     public ResponseEntity<List<Message>> listAllMessages();
     public ResponseEntity<String> receiveMessage(Long id);
     public ResponseEntity<String> readMessage(Long id);

     public Message translate(Message m);
}
