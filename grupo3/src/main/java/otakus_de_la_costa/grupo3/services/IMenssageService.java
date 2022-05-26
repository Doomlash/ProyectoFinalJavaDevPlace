package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.MessageRequest;

public interface IMenssageService {
	public List<Message> listAllMessages();
	public boolean createMessage(MessageRequest message);
	public void receiveMessage(Long id);
	public void readMessage(Long id);

}