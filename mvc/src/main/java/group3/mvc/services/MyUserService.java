package group3.mvc.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.RelationRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMyUser;
import reactor.core.publisher.Mono;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    @Override
    public Integer createU(MyUser myUser) {
        try{
            ResponseEntity<Integer> rCu = wCu.post()
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
                .retrieve()
                .toEntity(MyUser[].class)
                .block();
        List<MyUser> rLAul = Arrays.asList(rLAu.getBody());
        return rLAul;
    }

    @Override
    public Optional<MyUser> readU(Long idU) {
        try{
            ResponseEntity<Optional> rRu = wCu.get()
                    .uri("/"+idU)
                    .retrieve()
                    .toEntity(Optional.class)
                    .block();
            return rRu.getBody();
        }catch (WebClientResponseException e){
            return Optional.empty();
        }
    }

    @Override
    public Integer updateMyUser(MyUser myUser) {
        try {
            ResponseEntity<Integer> rUu = wCu.put()
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
    public Integer addC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rACu = wCu.post()
                    .uri("/contact")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
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
    public Integer removeC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rRCu = wCu.put()
                    .uri("/contact")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
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
    public Integer addB(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rABu = wCu.post()
                    .uri("/block")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
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
    public Integer removeB(RelationRequest rr) {
        try{
            ResponseEntity<Integer> rABu = wCu.put()
                    .uri("/block")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
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
