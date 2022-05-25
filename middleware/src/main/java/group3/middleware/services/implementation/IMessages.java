package group3.middleware.services.implementation;

import group3.middleware.model.Messages;
import java.util.List;

public interface IMessages {
     int create(Long idU, Messages m);
     List<Messages> messagesByContact(Long idU, Long idC);
     List<Messages> messageByGroup(Long idU,Long idG);
     List<Messages> getAll(Long idU);
     int delete(Long idM);
     Messages translate(Messages m);

    /*
    public List<Messages> messagesSentByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = wCs.get()
                .uri("/sent/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }

    public List<Messages> messagesReceivedByContact(Long idUser,Long idContact){
        Flux<Messages> messagesF = wCs.get()
                .uri("/received/" + idUser + "/" + idContact)
                .retrieve()
                .bodyToFlux(Messages.class);
        List<Messages> messages = messagesF.collectList().block();
        return messages;
    }*/
}
