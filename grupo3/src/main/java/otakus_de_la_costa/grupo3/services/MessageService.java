package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository mRepo;
	
	//GET ALL MESSAGES
	
	public List<Messages> findAll(){
		return mRepo.findAll();
	}
	
	//GET ONE MESSAGE
	
	public Optional<Messages> findById(Long id){
		if(mRepo.existsById(id)) {
			return mRepo.findById(id);
		}
		return null;
	}
	
	//POST MESSAGE
	
	public Messages save(Messages message) {
		return mRepo.save(message);
	}
	
	//UPDATE MESSAGE (PUT)
	
	public Messages update(Messages message) {
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
	}
}
