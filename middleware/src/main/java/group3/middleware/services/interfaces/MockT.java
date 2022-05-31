package group3.middleware.services.interfaces;

import group3.middleware.model.Message;
import group3.middleware.services.MessagesService;
import org.springframework.stereotype.Service;

@Service
public class MockT extends MessagesService {
    @Override
    public void translate(Message message, String lanU){
        message.setContent("texto traducido a " + lanU);
        message.setLanguage(lanU);
    }

}
