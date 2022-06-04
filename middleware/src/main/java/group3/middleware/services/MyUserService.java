package group3.middleware.services;

import group3.middleware.model.request.RelationRequest;
import group3.middleware.services.connection.ApiConnection;
import group3.middleware.model.MyUser;
import group3.middleware.services.connection.SecurityConnection;
import group3.middleware.services.interfaces.IMyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = new ApiConnection('u').getClient();

    @Override
    public ResponseEntity<MyUser[]> listAllUsers() {
        try {
            ResponseEntity<MyUser[]> rLAu = wCu.get()
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(MyUser[].class)
                    .block();
            return rLAu;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<MyUser> readUById(Long idU) {
        try{
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/"+idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<MyUser> readUByUsername(String username) {
        try{
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/byUsername/"+username)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu;
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Integer> updateMyUser(MyUser myUser) {
        try {
            ResponseEntity<Integer> rUu = wCu.put()
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(myUser),MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rUu;
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

    @Override
    public ResponseEntity<Integer> deleteU(Long idU) {
        try {
            ResponseEntity<Integer> rDu = wCu.delete()
                    .uri("/"+ idU)
                    .header("Authorization",SecurityConnection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rDu;
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

    ////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity<Integer> addC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rACu = wCu.post()
                    .uri("/contact")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rACu;
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

    @Override
    public ResponseEntity<Integer> removeC(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rRCu = wCu.put()
                    .uri("/contact")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rRCu;
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

    @Override
    public ResponseEntity<Integer> addB(RelationRequest rr) {
        try {
            ResponseEntity<Integer> rABu = wCu.post()
                    .uri("/block")
                    .header("Authorization",SecurityConnection.getToken())
                    .body(Mono.just(rr),RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rABu;
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

    @Override
    public ResponseEntity<Integer> removeB(RelationRequest rr) {
        try{
        ResponseEntity<Integer> rABu = wCu.put()
                .uri("/block")
                .header("Authorization",SecurityConnection.getToken())
                .body(Mono.just(rr),RelationRequest.class)
                .retrieve()
                .toEntity(Integer.class)
                .block();
        return rABu;
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
}
