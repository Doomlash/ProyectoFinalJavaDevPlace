package group3.mvc.services.implementation;

import group3.mvc.model.Message;
import group3.mvc.model.request.MessageRequest;

import java.util.List;


public interface IMessages {
     public Integer createMessage(Message ms);
     public List<Message> listAllMessages();
     public Integer receiveMessage(Message ms);
     public Integer readMessage(Message ms);

     public Message translate(Message m,String lanU);
}
