package group3.mvc.services;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import group3.mvc.model.LoginResponse;
import group3.mvc.model.Message;
import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.model.request.MessageForShow;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.connection.MiddleConection;

@Service
public class MiddleService {

    MiddleConection mc = new MiddleConection();

    private WebClient webClient = Connection.getClient();

    @Autowired
    MessagesService ms;

    public double getRatio(String id) {
        return mc.getRatio(id);
    }

    public void login() {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        LoginResponse response = webClient.post()
                    .uri("/security/login/"+username)
                    .headers(headers -> headers.setBasicAuth("admin", "admin_otaku"))
                    .retrieve()
                    .bodyToMono(LoginResponse.class)
                    .block();
        UserHolder.setCurrentUser(response.getUser());
        Connection.setToken(response.getToken());
        receiveMessages();
        List<Message> l = new LinkedList<>();
        for (Message m : UserHolder.getCurrentUser().getSent()) {
            l.add(prepareMessageForShow(m));
        }
        UserHolder.getCurrentUser().setSent(l);
        l = new LinkedList<>();
        for (Message m : UserHolder.getCurrentUser().getReceived()) {
            l.add(prepareMessageForShow(m));
        }
        UserHolder.getCurrentUser().setReceived(l);
    }

    public void register() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginResponse response = webClient.post()
                    .uri("/security/login/"+username)
                    .headers(headers -> headers.setBasicAuth("admin", "admin_otaku"))
                    .retrieve()
                    .bodyToMono(LoginResponse.class)
                    .block();
        UserHolder.setCurrentUser(response.getUser());
        Connection.setToken(response.getToken());
        receiveMessages();
    }

    private void receiveMessages(){
        MyUser user = UserHolder.getCurrentUser();
        for (Message message : user.getReceived()) {
            ms.receiveMessage(message);
        }
    }
    
    private MessageForShow prepareMessageForShow(Message m){
        String regex = "^/gif .*tenor.com.*-[0-9]*$";
        boolean isGif = false;
        double aspectRatio = 0;
        String gifId = "";
        if(Pattern.matches(regex, m.getContent())){
            isGif = true;
            gifId = m.getContent().substring(m.getContent().lastIndexOf('-')+1);
            aspectRatio = getRatio(gifId);
        }
        return new MessageForShow(m,isGif,gifId,aspectRatio);
    }
}