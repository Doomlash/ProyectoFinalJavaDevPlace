package group3.middleware.services;

import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.Group;
import group3.middleware.services.implementation.IGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = new Connection('g').getClient();

    public ResponseEntity<Integer> createG(GroupRequest group){
        ResponseEntity<Integer> response = wCg.post()
                .body(Mono.just(group),GroupRequest.class)
                .retrieve()
                .toEntity(Integer.class)
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

    public ResponseEntity<Integer> updateG(Group group) {
        ResponseEntity<Integer> ug = wCg.put()
                .body(Mono.just(group),Group.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return ug;
    }

    public ResponseEntity<Integer> deleteG(Long id){
        ResponseEntity<Integer> dg = wCg.delete()
                .uri("/"+id)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return dg;
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    public ResponseEntity<Integer> addM(GroupMemberRequest gmr){
        ResponseEntity<Integer> response = wCg.post()
                .uri("/member")
                .body(Mono.just(gmr),GroupMemberRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return response;
    }

    public ResponseEntity<Integer> removeM(GroupMemberRequest gmr){
        ResponseEntity<Integer> response = wCg.put()
                .uri("/member")
                .body(Mono.just(gmr),GroupMemberRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return response;
    }
}
