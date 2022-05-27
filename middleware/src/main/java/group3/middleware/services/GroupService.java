package group3.middleware.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import group3.middleware.model.GroupMemberRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.Group;
import group3.middleware.services.implementation.IGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GroupService implements IGroup {

    private WebClient wCg = new Connection('g').getClient();

    public ResponseEntity<String> createG(Group group){
        ResponseEntity<String> response = wCg.post()
                .body(Mono.just(group),Group.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response;
    }

    public ResponseEntity<List<Group>> listAllG(){
        Flux<Group> response = wCg.get()
                .retrieve()
                .bodyToFlux(Group.class);
        List<Group> groups = response.collectList().block();

        ResponseEntity<String> rta = wCg.get()
                .retrieve()
                .toEntity(String.class)
                .block();

        ResponseEntity<List<Group>> rt = ResponseEntity.status(rta.getStatusCode()).body(groups);

        return rt;
    }

    public ResponseEntity<Group> readG(Long id){
        ResponseEntity<Group> rg = wCg.get()
                .uri("/" + id)
                .retrieve()
                .toEntity(Group.class)
                .block();
        return rg;
    }

    public ResponseEntity<String> updateG(Group group) {
        ResponseEntity<String> ug = wCg.put()
                .body(Mono.just(group),Group.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return ug;
    }

    public ResponseEntity<String> deleteG(Long id){
        ResponseEntity<String> dg = wCg.delete()
                .uri("/"+id)
                .retrieve()
                .toEntity(String.class)
                .block();
        return dg;
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    public ResponseEntity<String> addM(GroupMemberRequest gmr){
        ResponseEntity<String> response = wCg.post()
                .uri("/member")
                .body(Mono.just(gmr),GroupMemberRequest.class)
                .retrieve().toEntity(String.class).block();
        return response;
    }

    public ResponseEntity<String> removeM(GroupMemberRequest gmr){
        ResponseEntity<String> response = wCg.delete()
                .uri("/member",Mono.just(gmr),GroupMemberRequest.class)
                .retrieve().toEntity(String.class).block();
        return response;
    }
}
