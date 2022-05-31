package group3.middleware.services;

import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import group3.middleware.services.connection.ApiConnection;
import group3.middleware.model.Group;
import group3.middleware.services.connection.SecurityConnection;
import group3.middleware.services.interfaces.IGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = new ApiConnection('g').getClient();

    public ResponseEntity<Integer> createG(GroupRequest group){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(group),GroupRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Group[]> listAllG(){
        try {
            ResponseEntity<Group[]> rLAg = wCg.get()
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Group[].class)
                    .block();
            return rLAg;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return null;
        }
    }

    public ResponseEntity<Group> readG(Long id){
        try{
            ResponseEntity<Group> rg = wCg.get()
                    .uri("/" + id)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Group.class)
                    .block();
            return rg;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Integer> updateG(Group group) {
        try{
            ResponseEntity<Integer> ug = wCg.put()
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(group),Group.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return ug;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }

    }

    public ResponseEntity<Integer> deleteG(Long id){
        try {
            ResponseEntity<Integer> dg = wCg.delete()
                    .uri("/"+id)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return dg;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode()).build();
        }
    }

    public ResponseEntity<Object> isAdmin(Long idG,Long idU){
        try {
            ResponseEntity<Object> dg = wCg.get()
                    .uri("/"+idG + "/isAdmin/"+idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return dg;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Object> changeAdmin(Long idG,Long idU){
        try {
            ResponseEntity<Object> dg = wCg.put()
                    .uri("/"+idG + "/changeAdmin/"+idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Object.class)
                    .block();
            return dg;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }




///////////////////////////////////////////////////////////////////////////////////////////////

    public ResponseEntity<Integer> addM(GroupMemberRequest gmr){
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/member")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(gmr),GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }

    public ResponseEntity<Integer> removeM(GroupMemberRequest gmr) {
        try {
            ResponseEntity<Integer> response = wCg.put()
                    .uri("/member")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(gmr), GroupMemberRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build();
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString())));
        }
    }
}
