package otakus_de_la_costa.grupo3.services;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.database.MessengerJPA;
import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.MessageRequest;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;

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
	public void receiveMessage(Long id) {
		mr.receiveMessage(id);
	}

	@Override
	@Transactional
	public void readMessage(Long id) {
		mr.readMessage(id);
	}
}
	