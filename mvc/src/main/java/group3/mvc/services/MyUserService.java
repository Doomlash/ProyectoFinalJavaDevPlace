package group3.mvc.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import group3.mvc.model.request.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMyUser;
import reactor.core.publisher.Mono;

@Service
public class MyUserService implements IMyUser {
    private WebClient wCu = Connection.getClient();


    @Override
    public List<MyUser> listAllUsers() {
        try {
            ResponseEntity<MyUser[]> rLAu = wCu.get()
                    .uri("/users")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .retrieve()
                    .toEntity(MyUser[].class)
                    .block();
            List<MyUser> rLAul = Arrays.asList(rLAu.getBody());
            return rLAul;
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return listAllUsers();
            }
            return Collections.emptyList();
        }
    }

    @Override
    public MyUser readUById(Long idU) {
        try {
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/users/" + idU)
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu.getBody();
        } catch (WebClientResponseException e) {

            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return readUById(idU);
            }
            return null;
        }
    }

    @Override
    public MyUser readUByUsername(String username) {
        try {
            ResponseEntity<MyUser> rRu = wCu.get()
                    .uri("/users/byUsername/" + username)
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .retrieve()
                    .toEntity(MyUser.class)
                    .block();
            return rRu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return readUByUsername(username);
            }
            return null;
        }
    }

    @Override
    public Integer updateMyUser(MyUser myUser) {
        try {
            ResponseEntity<Integer> rUu = wCu.put()
                    .uri("/users")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .body(Mono.just(myUser), MyUser.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            UserHolder.setCurrentUser(myUser);

            return rUu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return updateMyUser(myUser);

            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer deleteU(Long idU) {
        try {
            ResponseEntity<Integer> rDu = wCu.delete()
                    .uri("/users/" + idU)
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();
            return rDu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return deleteU(idU);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public Integer addC(MyUser contact) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), contact.getId());
        try {
            ResponseEntity<Integer> rACu = wCu.post()
                    .uri("/users/contact")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .body(Mono.just(rr), RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            createContact(contact);

            return rACu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return addC(contact);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer removeC(Long idC) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idC);
        try {
            ResponseEntity<Integer> rRCu = wCu.put()
                    .uri("/users/contact")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .body(Mono.just(rr), RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            removeContact(idC);

            return rRCu.getBody();


        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return removeC(idC);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer addB(Long idB) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idB);
        try {
            ResponseEntity<Integer> rABu = wCu.post()
                    .uri("/users/block")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .body(Mono.just(rr), RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            addBlock(idB);

            return rABu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return addB(idB);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    @Override
    public Integer removeB(Long idB) {
        RelationRequest rr = new RelationRequest(UserHolder.getCurrentUser().getId(), idB);
        try {
            ResponseEntity<Integer> rABu = wCu.put()
                    .uri("/users/block")
                    .header("Authorization", "Bearer " + Connection.getToken())
                    .body(Mono.just(rr), RelationRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            removeBlock(idB);

            return rABu.getBody();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if (e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) {
                Connection.generateToken();
                return removeB(idB);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }


    /**
     * FUNCIONES AUXILIARES
     */
    private void createContact(MyUser user) {
        SimpleUserResponse simpleUserResponse = new SimpleUserResponse(user.getId(), user.getUsername(), user.getProfileImage());
        UserHolder.getCurrentUser().addContact(simpleUserResponse);
    }

    private void removeContact(Long id) {
        for (SimpleUserResponse contact : UserHolder.getCurrentUser().getContacts()) {
            if (contact.getId() == id) {
                UserHolder.getCurrentUser().getContacts().remove(contact);
                break;
            }
        }
    }

    private void addBlock(Long id) {
        for (SimpleUserResponse contact : UserHolder.getCurrentUser().getContacts()) {
            if (contact.getId() == id) {
                UserHolder.getCurrentUser().addBlock(contact);
                break;
            }
        }
    }

    private void removeBlock(Long id) {
        for (SimpleUserResponse blocked : UserHolder.getCurrentUser().getBlocks()) {
            if (blocked.getId() == id) {
                UserHolder.getCurrentUser().getBlocks().remove(blocked);
                break;
            }
        }
    }

    public SimpleUserResponse retrieveContact(String username) {
        for (SimpleUserResponse contact : UserHolder.getCurrentUser().getContacts()) {
            if (contact.getUsername().compareTo(username) == 0) {
                return contact;
            }
        }
        return null;
    }

}


