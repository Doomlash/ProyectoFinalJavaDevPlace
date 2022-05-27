package group3.middleware.services;

import group3.middleware.model.RelationRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.MyUser;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new Connection('u').getClient();

    @Override
    public ResponseEntity<String> createU(MyUser myUser) {
        ResponseEntity<String> rCu = wCu.post()
                .body(Mono.just(myUser),MyUser.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rCu;
    }

    @Override
    public ResponseEntity<List<MyUser>> listAllUsers() {
        Flux<MyUser> fu = wCu.get()
                .retrieve()
                .bodyToFlux(MyUser.class);
        List<MyUser> users = fu.collectList().block();

        ResponseEntity<String> rSu = wCu.get()
                .retrieve()
                .toEntity(String.class)
                .block();

        ResponseEntity<List<MyUser>> rLAu = new ResponseEntity<>(users,rSu.getStatusCode());
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
    public ResponseEntity<String> updateMyUser(MyUser myUser) {
        ResponseEntity<String> rUu = wCu.put()
                .body(Mono.just(myUser),MyUser.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rUu;
    }

    @Override
    public ResponseEntity<String> deleteU(Long idU) {
        ResponseEntity<String> rDu = wCu.delete()
                .uri("/"+ idU)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rDu;
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity<String> addC(RelationRequest rr) {
        ResponseEntity<String> rACu = wCu.post()
                .uri("/contact")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rACu;
    }

    @Override
    public ResponseEntity<String> removeC(RelationRequest rr) {
        System.out.println("entre");
        ResponseEntity<String> rRCu = wCu.delete()
                .uri("/contact",Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        System.out.println("sali");
        return rRCu;
    }

    @Override
    public ResponseEntity<String> addB(RelationRequest rr) {
        ResponseEntity<String> rABu = wCu.post()
                .uri("/block")
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rABu;
    }

    @Override
    public ResponseEntity<String> removeB(RelationRequest rr) {
        ResponseEntity<String> rABu = wCu.delete()
                .uri("/block",Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return rABu;
    }
}
