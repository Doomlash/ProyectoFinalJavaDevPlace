package group3.mvc.services;

import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.model.request.RelationRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.connection.SecurityConnection;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    @Override
    public Integer createU(MyUser myUser) {
        try{
            ResponseEntity<Integer> rCu = wCu.post()
                    .header("Authorization", SecurityConnection.getToken())
                    .body(Mono.just(myUser),MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }

    }

    @Override
    public List<MyUser> listAllUsers() {
        ResponseEntity<MyUser[]> rLAu = wCu.get()
                .header("Authorization",SecurityConnection.getToken())
                .retrieve()
                .toEntity(MyUser[].class)
                .block();
        List<MyUser> rLAul = Arrays.asList(rLAu.getBody());
        return rLAul;
    }

    @Override
    public MyUser readUById(Long idU) {
        try{
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/"+idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu.getBody();
        }catch (WebClientResponseException e){
            return new MyUser();
        }
    }

    @Override
    public MyUser readUByUsername(String username) {
        try{
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/byUsername/"+username)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu.getBody();
        }catch (WebClientResponseException e){
            return new MyUser();
        }
    }

    @Override
    public Integer updateMyUser(MyUser myUser) {
        try {
            ResponseEntity<Integer> rUu = wCu.put()
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(myUser),MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rUu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer deleteU(Long idU) {
        try {
            ResponseEntity<Integer> rDu = wCu.delete()
                    .uri("/"+ idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rDu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public Integer addC(Long idC) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idC);
        try {
            ResponseEntity<Integer> rACu = wCu.post()
                    .uri("/contact")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            MyUser contact = this.readUById(idC);
            UserHolder.getCurrentUser().addContact(contact.getId(), contact.getUsername());
            System.out.println(UserHolder.getCurrentUser().toString());

            return rACu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer removeC(Long idC) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idC);
        try {
            ResponseEntity<Integer> rRCu = wCu.put()
                    .uri("/contact")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            UserHolder.getCurrentUser().getContacts().remove(idC);
            System.out.println(UserHolder.getCurrentUser().toString());

            return rRCu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer addB(Long idC) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idC);
        try {
            ResponseEntity<Integer> rABu = wCu.post()
                    .uri("/block")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            MyUser contact = this.readUById(idC);
            UserHolder.getCurrentUser().addBlock(contact.getId(), contact.getUsername());
            System.out.println(UserHolder.getCurrentUser().toString());

            return rABu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer removeB(Long idC) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idC);
        try{
            ResponseEntity<Integer> rABu = wCu.put()
                    .uri("/block")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            UserHolder.getCurrentUser().getContacts().remove(idC);
            System.out.println(UserHolder.getCurrentUser().toString());

            return rABu.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }
}
