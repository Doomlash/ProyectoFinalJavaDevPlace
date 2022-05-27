package group3.mvc.services;

import group3.mvc.model.Group;
import group3.mvc.model.GroupMemberRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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

    public ResponseEntity<Group[]> listAllG(){
        ResponseEntity<Group[]> rLAg = wCg.get()
                .retrieve()
                .toEntity(Group[].class)
                .block();
        return rLAg;
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
                .retrieve()
                .toEntity(String.class)
                .block();
        return response;
    }

    public ResponseEntity<String> removeM(GroupMemberRequest gmr){
        ResponseEntity<String> response = wCg.put()
                .uri("/member")
                .body(Mono.just(gmr),GroupMemberRequest.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response;
    }
}
