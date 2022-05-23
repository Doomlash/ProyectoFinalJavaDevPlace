package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Messages;

public interface IMenssageService {
	public Messages createMessage(Messages message,Long userId,Long contId);
	public List<Messages> listAllMessages();
	public Messages findMessageById(Long id);
	public Messages updateMessage(Messages message, Long id);
	public boolean deleteMessage(Long id);
	
}
