package group3.middleware.services;

import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import group3.middleware.services.connection.Connection;
import group3.middleware.model.Group;
import group3.middleware.services.implementation.IGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = new Connection('g').getClient();

    public ResponseEntity<Integer> createG(GroupRequest group){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .body(Mono.just(group),GroupRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Group[]> listAllG(){
        ResponseEntity<Group[]> rLAg = wCg.get()
                .retrieve()
                .toEntity(Group[].class)
                .block();
        return rLAg;
    }

    public ResponseEntity<Group> readG(Long id){
        try{
            ResponseEntity<Group> rg = wCg.get()
                    .uri("/" + id)
                    .retrieve()
                    .toEntity(Group.class)
                    .block();
            return rg;
        }catch (WebClientResponseException e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Integer> updateG(Group group) {
        try{
            ResponseEntity<Integer> ug = wCg.put()
                    .body(Mono.just(group),Group.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return ug;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }

    }

    public ResponseEntity<Integer> deleteG(Long id){
        try {
            ResponseEntity<Integer> dg = wCg.delete()
                    .uri("/"+id)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return dg;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .build();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    public ResponseEntity<Integer> addM(GroupMemberRequest gmr){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/member")
                    .body(Mono.just(gmr),GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Integer> removeM(GroupMemberRequest gmr){
        try {
            ResponseEntity<Integer> response = wCg.put()
                    .uri("/member")
                    .body(Mono.just(gmr),GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        }catch (WebClientResponseException e){
        return ResponseEntity
                .status(e.getStatusCode())
                .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }
}
