package group3.middleware.services;

import group3.middleware.model.request.RelationRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.MyUser;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    @Override
    public ResponseEntity<Integer> createU(MyUser myUser) {
        ResponseEntity<Integer> rCu = wCu.post()
                .body(Mono.just(myUser),MyUser.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rCu;
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
        ResponseEntity<MyUser> rRu = wCu.get()
                .uri("/"+idU)
                .retrieve()
                .toEntity(MyUser.class)
                .block();
        return rRu;
    }

    @Override
    public ResponseEntity<Integer> updateMyUser(MyUser myUser) {
        ResponseEntity<Integer> rUu = wCu.put()
                .body(Mono.just(myUser),MyUser.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rUu;
    }

    @Override
    public ResponseEntity<Integer> deleteU(Long idU) {
        ResponseEntity<Integer> rDu = wCu.delete()
                .uri("/"+ idU)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rDu;
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity<Integer> addC(RelationRequest rr) {
        ResponseEntity<Integer> rACu = wCu.post()
                .uri("/contact")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rACu;
    }

    @Override
    public ResponseEntity<Integer> removeC(RelationRequest rr) {
        ResponseEntity<Integer> rRCu = wCu.put()
                .uri("/contact")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rRCu;
    }

    @Override
    public ResponseEntity<Integer> addB(RelationRequest rr) {
        ResponseEntity<Integer> rABu = wCu.post()
                .uri("/block")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rABu;
    }

    @Override
    public ResponseEntity<Integer> removeB(RelationRequest rr) {
        ResponseEntity<Integer> rABu = wCu.delete()
                .uri("/block",Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rABu;
    }
}
