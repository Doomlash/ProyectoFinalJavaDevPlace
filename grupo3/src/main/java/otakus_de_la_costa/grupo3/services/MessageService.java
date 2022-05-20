package otakus_de_la_costa.grupo3.services;




import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;

@Service
public class MessageService implements IMenssageService {
	@Autowired
	private MessageRepository mRepo;
	//GUARDADO DEL OBJETO JSON A JPA
	public Messages createMessage(Messages message) {
		MessageJPA myMessageJPA= mapearMessageJPA(message);
		MessageJPA  newMessageJPA = mRepo.save(myMessageJPA);
		Messages response = mapearMessage(newMessageJPA);
		return response;
		
	}
	//MAPEOS DEL JSON AL JPA
	private MessageJPA mapearMessageJPA(Messages message) {
		MessageJPA myMessageJPA = new MessageJPA();
		myMessageJPA.setContent(message.getContent());
		myMessageJPA.setCreationDate(message.getCreationDate());
		myMessageJPA.setLanguage(message.getLanguage());
		myMessageJPA.setReadDate(message.getReadDate());
		myMessageJPA.setReceptionDate(message.getReceptionDate());
		return myMessageJPA;
	}
	//MAPEOS DEL JPA AL JSON
	private Messages mapearMessage(MessageJPA newMessageJPA) {
		Messages myMessage = new Messages();
		myMessage.setId(newMessageJPA.getId());
		myMessage.setContent(newMessageJPA.getContent());
		myMessage.setCreationDate(newMessageJPA.getCreationDate());
		myMessage.setLanguage(newMessageJPA.getLanguage());
		myMessage.setReadDate(newMessageJPA.getReadDate());
		myMessage.setReceptionDate(newMessageJPA.getReceptionDate());
		return myMessage;
	}
	@Override
	public List<Messages> listAllMessages() {
		List<MessageJPA> messages = mRepo.findAll();
		return messages.stream().map(messageJPA -> mapearMessage(messageJPA)).collect(Collectors.toList());
	}
	
	@Override
	public Messages findMessageById(Long id) {
		Optional<MessageJPA> myMessageJPA= mRepo.findById(id);
		if(myMessageJPA.isPresent()) {
		return mapearMessage(myMessageJPA.get());
		}
		return null;
	}
	@Override
	public Messages updateMessage(Messages message, Long id) {
		Optional<MessageJPA> myMessageJPA = mRepo.findById(id);
		myMessageJPA.get().setContent(message.getContent());
		myMessageJPA.get().setCreationDate(message.getCreationDate());
		myMessageJPA.get().setLanguage(message.getLanguage());
		myMessageJPA.get().setReadDate(message.getReadDate());
		myMessageJPA.get().setReceptionDate(message.getReceptionDate());
		MessageJPA updateMessage = mRepo.save(myMessageJPA.get());
		return mapearMessage(updateMessage);
	}
	
	@Override
	public boolean deleteMessage(Long id) {
		Optional<MessageJPA> message=mRepo.findById(id);
		if(message.isPresent())
		{
			mRepo.delete(message.get());
			return true;
		}
		return false;
		
	}
}
	