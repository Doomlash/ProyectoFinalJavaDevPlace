package otakus_de_la_costa.grupo3.services.implementation;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.request.MessageRequest;


public interface IMenssageService{
	public List<Message> listAllMessages();
	public boolean createMessage(MessageRequest message);
	public int receiveMessage(Long id);
	public int readMessage(Long id);

}