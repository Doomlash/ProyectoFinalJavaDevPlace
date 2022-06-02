package group3.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import group3.mvc.model.LoginResponse;
import group3.mvc.model.Message;
import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.connection.MiddleConection;

@Service
public class MiddleService {

    MiddleConection mc = new MiddleConection();

    private WebClient webClient = Connection.getClient();

    @Autowired
    MessagesService ms;

    public Object getRatio(String id) {
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
    
}