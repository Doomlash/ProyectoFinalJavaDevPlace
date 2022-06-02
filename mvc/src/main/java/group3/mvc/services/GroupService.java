package group3.mvc.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.Group;
import group3.mvc.model.request.GroupMemberRequest;
import group3.mvc.model.request.GroupRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IGroup;
import reactor.core.publisher.Mono;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = Connection.getClient();


    public Integer createG(GroupRequest group){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/groups")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(group),GroupRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public List<Group> listAllG(){
        try {

        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
            }
            return Collections.emptyList();
        }
        ResponseEntity<Group[]> rLAg = wCg.get()
                .uri("/groups")
                .header("Authorization", "Bearer "+ Connection.getToken())
                .retrieve()
                .toEntity(Group[].class)
                .block();
        List<Group> rLAgl = Arrays.asList(rLAg.getBody());
        return rLAgl;
    }

    public Group readG(Long id){
        try{
            ResponseEntity<Group> rg = wCg.get()
                    .uri("/groups/" + id)
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Group.class)
                    .block();
            return rg.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
            }

            return null;
        }
    }

    public Integer updateG(Group group) {
        try{
            ResponseEntity<Integer> ug = wCg.put()
                    .uri("/groups")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(group),Group.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return ug.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }

    }

    public Integer deleteG(Long id){
        try {
            ResponseEntity<Integer> dg = wCg.delete()
                    .uri("/groups/"+id)
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return dg.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.INTERNAL_SERVER_ERROR) ==0){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode()).build().getStatusCodeValue();
        }
    }

    public Object isAdmin(Long idG, Long idU){
        try {
            ResponseEntity<Object> response = wCg.get()
                    .uri("/groups/"+idG+"/isAdmin/"+idU)
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Object changeAdmin(Long idG, Long idU){
        try {
            ResponseEntity<Object> response = wCg.put()
                    .uri("/groups/"+idG+"/changeAdmin/"+idU)
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    public Integer addM(Long idG,Long idC){
        GroupMemberRequest gmr = new GroupMemberRequest(idG,idC,false);
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/groups/member")
                    .header("Authorization", "Bearer "+ Connection.getToken())
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
                    .uri("/groups/member")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(gmr), GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build().getStatusCodeValue();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }
}
