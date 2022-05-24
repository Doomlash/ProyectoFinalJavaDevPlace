package group3.middleware.services;

import group3.middleware.model.Group;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GroupService {
    private WebClient webClient = org.springframework.web.reactive.function.client.WebClient.builder().baseUrl("http://localhost:8080/api/groups")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE).build();

    public int createGroup(Group group){
        ResponseEntity<String> response = webClient.post()
                .uri("/addGroup")
                .body(group,Group.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public Group readGroup(Long id){
        Group group = webClient.get()
                .uri("/"+ id)
                .retrieve()
                .bodyToMono(Group.class)
                .block();
        return group;
    }

    public int updateGroup(Long id, Group group) {
        ResponseEntity<String> response = webClient.put()
                .uri("/" + id)
                .body(group, Group.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response.getStatusCodeValue();
    }

    public int deleteGroup(Long id){
        ResponseEntity<String> response = webClient.delete()
                .uri("/"+id)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    //////////////////////////////
    public int addUserToGroup(Long idUser,Long idGroup){
        ResponseEntity<String> response = webClient.get()
                .uri("/"+idUser+"/"+idGroup)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteUserToGroup(Long idUser,Long idGroup){
        ResponseEntity<String> response = webClient.delete()
                .uri("/"+idUser+"/"+idGroup)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }
}
