package group3.middleware.services;

import group3.middleware.model.MyUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MyUserService {
    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/users")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE).build();

    public int createUser(MyUser user){
        ResponseEntity<String> response = webClient.post()
                .uri("/addUser")
                .body(user,MyUser.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public MyUser readUser(Long id){
        MyUser user = webClient.get()
                .uri("/"+ id)
                .retrieve()
                .bodyToMono(MyUser.class)
                .block();
        return user;
    }

    public int updateUser(Long id, MyUser user) {
        ResponseEntity<String> response = webClient.put()
                .uri("/" + id)
                .body(user, MyUser.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response.getStatusCodeValue();
    }

    public int deleteUser(Long id){
        ResponseEntity<String> response = webClient.delete()
                .uri("/"+id)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    ///////////////////////////////////////////////////////////////////////////
    public int addContact(Long idUser,Long idContact){
        ResponseEntity<String> response = webClient.post()
                .uri("/addContact/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int addBlock(Long idUser,Long idContact){
        ResponseEntity<String> response = webClient.post()
                .uri("/addBlock/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteContact(Long idUser,Long idContact){
        ResponseEntity<String> response = webClient.post()
                .uri("/deleteContact/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteBlock(Long idUser,Long idContact){
        ResponseEntity<String> response = webClient.post()
                .uri("/deleteBlock/"+idUser+"/"+idContact)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }




}
