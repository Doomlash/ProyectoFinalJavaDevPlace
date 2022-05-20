package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository mRepo;
	
	public Messages createMessage(Messages message) {
		MessageJPA myMessageJPA= mapearMessageJPA(message);
		MessageJPA  newMessageJPA = mRepo.save(myMessageJPA);
		Messages response = mapearMessage(newMessageJPA);
		return response;
		
	}
	
	private MessageJPA mapearMessageJPA(Messages message) {
		MessageJPA myMessageJPA = new MessageJPA();
		myMessageJPA.setContent(message.getContent());
		myMessageJPA.setCreationDate(message.getCreationDate());
		myMessageJPA.setLanguage(message.getLanguage());
		myMessageJPA.setReadDate(message.getReadDate());
		myMessageJPA.setReceptionDate(message.getReceptionDate());
		return myMessageJPA;
	}

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
	/*
	//GET ALL MESSAGES
	
	public List<MessageJPA> findAll(){
		return mRepo.findAll();
	}
	
	//GET ONE MESSAGE
	
	public Optional<MessageJPA> findById(Long id){
		if(mRepo.existsById(id)) {
			return mRepo.findById(id);
		}
		return null;
	}
	
	//POST MESSAGE
	
	public MessageJPA save(MessageJPA message) {
		return mRepo.save(message);
	}
	
	//UPDATE MESSAGE (PUT)
	
	public MessageJPA update(MessageJPA message) {
		if(mRepo.existsById(message.getId())) {
			return mRepo.save(message);
		}
		return null;
	}
	
	//DELETE MESSSAGE
	
	public boolean delete(Long id) {
		if(mRepo.existsById(id)) {
			mRepo.deleteById(id);
			return true;
		}
		return false;
	}*/


}
