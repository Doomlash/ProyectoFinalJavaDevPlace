package group3.mvc.services.implementation;

import group3.mvc.model.Message;
import group3.mvc.model.request.MessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IMessages {
     public Integer createMessage(MessageRequest message);
     public List<Message> listAllMessages();
     public Integer receiveMessage(Long id);
     public Integer readMessage(Long id);

     public Message translate(Message m,String lanU);
}
