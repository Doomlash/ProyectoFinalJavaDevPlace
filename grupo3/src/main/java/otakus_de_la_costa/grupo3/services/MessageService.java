package otakus_de_la_costa.grupo3.services;

import static otakus_de_la_costa.grupo3.model.Constants.NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.database.MessengerJPA;
import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.request.MessageRequest;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;
import otakus_de_la_costa.grupo3.services.implementation.IMenssageService;

@Service
public class MessageService implements IMenssageService {
	@Autowired
	private MessageRepository mr;

	private Message mapMessageJPAToMessage(MessageJPA m){
		return new Message(
				m.getId(),
				m.getContent(),
				m.getLanguage(),
				m.getCreationDate(),
				m.getReceptionDate(),
				m.getReadDate(),
				m.getSender().getId(),
				m.getReceiver().getId()
		);
	}

	@Override
	public List<Message> listAllMessages() {
		List<MessageJPA> l = mr.findAll();
		List<Message> result = new LinkedList<>();
		for (MessageJPA messageJPA : l) {
			result.add(mapMessageJPAToMessage(messageJPA));
		}
		return result;
	}

	@Override
	public boolean createMessage(MessageRequest message) {
		MessageJPA m = new MessageJPA();
		m.setLanguage(message.getLanguage());
		m.setContent(message.getContent());
		m.setSender(new MessengerJPA(message.getSenderId()));
		m.setReceiver(new MessengerJPA(message.getReceiverId()));
		System.out.println("--------------------------------------------------");
		System.out.println(m.toString());
		if (mr.save(m) != null) {
			System.out.println(m.toString());
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public int receiveMessage(Long id) {
		if(mr.findById(id).isEmpty()){
			return NOT_FOUND;
		}
		mr.receiveMessage(id);
		return OK;
	}

	@Override
	@Transactional
	public int readMessage(Long id) {
		if(mr.findById(id).isEmpty()){
			return NOT_FOUND;
		}
		mr.readMessage(id);
		return OK;
	}
}