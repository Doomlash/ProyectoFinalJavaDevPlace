package group3.mvc.services;

import group3.mvc.model.Group;
import group3.mvc.model.request.GroupMemberRequest;
import group3.mvc.model.request.GroupRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = new Connection('g').getClient();


    public Integer createG(GroupRequest group){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .body(Mono.just(group),GroupRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public List<Group> listAllG(){
        ResponseEntity<Group[]> rLAg = wCg.get()
                .retrieve()
                .toEntity(Group[].class)
                .block();
        List<Group> rLAgl = Arrays.asList(rLAg.getBody());
        return rLAgl;
    }

    public Optional<Group> readG(Long id){
        try{
            ResponseEntity<Optional> rg = wCg.get()
                    .uri("/" + id)
                    .retrieve()
                    .toEntity(Optional.class)
                    .block();
            return rg.getBody();
        }catch (WebClientResponseException e){
            return Optional.empty();
        }
    }

    public Integer updateG(Group group) {
        try{
            ResponseEntity<Integer> ug = wCg.put()
                    .body(Mono.just(group),Group.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return ug.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }

    }

    public Integer deleteG(Long id){
        try {
            ResponseEntity<Integer> dg = wCg.delete()
                    .uri("/"+id)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return dg.getBody();
        }catch (WebClientResponseException e){
            return ResponseEntity
                    .status(e.getStatusCode()).build().getStatusCodeValue();
        }
    }

    public Object isAdmin(Long idG, Long idU){
        try {
            ResponseEntity<Object> response = wCg.get()
                    .uri("/"+idG+"/isAdmin/"+idU)
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Object changeAdmin(Long idG, Long idU){
        try {
            ResponseEntity<Object> response = wCg.put()
                    .uri("/"+idG+"/changeAdmin/"+idU)
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    public Integer addM(GroupMemberRequest gmr){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/member")
                    .body(Mono.just(gmr),GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Integer removeM(GroupMemberRequest gmr) {
        try {
            ResponseEntity<Integer> response = wCg.put()
                    .uri("/member")
                    .body(Mono.just(gmr), GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }
}
