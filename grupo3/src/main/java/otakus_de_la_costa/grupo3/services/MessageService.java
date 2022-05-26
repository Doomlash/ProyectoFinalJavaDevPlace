package otakus_de_la_costa.grupo3.services;




import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;

@Service
public class MessageService implements IMenssageService {
	@Autowired
	private MessageRepository mr;
	

    @Override
    public List<Message> listAllMessages() {
        List<MessageJPA> l = mr.findAll();
        List<Message> result = new LinkedList<>();
        for (MessageJPA messageJPA : l) {
            Message m = new Message(messageJPA);
            result.add(m);
        }
        return result;
    }

    @Override
    public Message findMessageById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Message updateMessage(Message message, Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteMessage(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Message createMessage(Message message) {
        MessageJPA m = new MessageJPA();
        m.setLanguage(message.getLanguage());
        m.setContent(message.getContent());
        return null;
    }
}
	