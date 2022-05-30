package group3.middleware.services;

import group3.middleware.model.request.RelationRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.MyUser;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    @Override
    public ResponseEntity<Integer> createU(MyUser myUser) {
        try{
            ResponseEntity<Integer> rCu = wCu.post()
                    .body(Mono.just(myUser),MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rCu;
        }catch (WebClientResponseException e){
            ResponseEntity<Integer> rCu = ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
            return rCu;
        }

    }

    @Override
    public ResponseEntity<MyUser[]> listAllUsers() {
        ResponseEntity<MyUser[]> rLAu = wCu.get()
                .retrieve()
                .toEntity(MyUser[].class)
                .block();
        return rLAu;
    }

    @Override
    public ResponseEntity<MyUser> readU(Long idU) {
        try{
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/"+idU)
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu;
        }catch (WebClientResponseException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Integer> updateMyUser(MyUser myUser) {
        try {
            ResponseEntity<Integer> rUu = wCu.put()
                    .body(Mono.just(myUser),MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rUu;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    @Override
    public ResponseEntity<Integer> deleteU(Long idU) {
        try {
            ResponseEntity<Integer> rDu = wCu.delete()
                    .uri("/"+ idU)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rDu;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity<Integer> addC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rACu = wCu.post()
                    .uri("/contact")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rACu;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    @Override
    public ResponseEntity<Integer> removeC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rRCu = wCu.put()
                    .uri("/contact")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRCu;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    @Override
    public ResponseEntity<Integer> addB(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rABu = wCu.post()
                    .uri("/block")
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rABu;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    @Override
    public ResponseEntity<Integer> removeB(RelationRequest rr) {
        try{
        ResponseEntity<Integer> rABu = wCu.put()
                .uri("/block")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rABu;
    }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }
}
