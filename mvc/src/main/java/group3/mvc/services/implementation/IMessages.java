package group3.mvc.services.implementation;

import java.util.List;

import group3.mvc.model.Message;


public interface IMessages {
     public Integer createMessage(Message ms);
     public List<Message> listAllMessages();
     public Integer receiveMessage(Message ms);
     public Integer readMessage(Message ms);

     public Message translate(Message m,String lanU);
    public List<Message> filterMessagesContact(Long id);
    public Message getMessage(Long messageId);
}
