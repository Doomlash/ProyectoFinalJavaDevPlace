package group3.middleware.services;

import group3.middleware.services.connection.Connection;
import group3.middleware.model.MyUser;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    public int createUser(MyUser user){
        ResponseEntity<String> response = wCu.post()
                .uri("/addUser")
                .body(user,MyUser.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public MyUser readUser(Long id){
        MyUser user = wCu.get()
                .uri("/"+ id)
                .retrieve()
                .bodyToMono(MyUser.class)
                .block();
        return user;
    }

    public int updateUser(Long id, MyUser user) {
        ResponseEntity<String> response = wCu.put()
                .uri("/" + id)
                .body(user, MyUser.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response.getStatusCodeValue();
    }

    public int deleteUser(Long id){
        ResponseEntity<String> response = wCu.delete()
                .uri("/"+id)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    ///////////////////////////////////////////////////////////////////////////
    public int addContact(Long idUser,Long idContact){
        ResponseEntity<String> response = wCu.post()
                .uri("/addContact/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int addBlock(Long idUser,Long idContact){
        ResponseEntity<String> response = wCu.post()
                .uri("/addBlock/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteContact(Long idUser,Long idContact){
        ResponseEntity<String> response = wCu.post()
                .uri("/deleteContact/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteBlock(Long idUser,Long idContact){
        ResponseEntity<String> response = wCu.post()
                .uri("/deleteBlock/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }




}
