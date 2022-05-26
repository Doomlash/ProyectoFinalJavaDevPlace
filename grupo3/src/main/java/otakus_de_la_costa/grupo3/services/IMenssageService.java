package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Message;

public interface IMenssageService {
	public List<Message> listAllMessages();
	public Message findMessageById(Long id);
	public Message updateMessage(Message message, Long id);
	public boolean deleteMessage(Long id);
    public Message createMessage(Message message);
	
}
