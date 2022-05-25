package group3.middleware.services;

import group3.middleware.services.connection.Connection;
import group3.middleware.model.Group;
import group3.middleware.services.implementation.IGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GroupService implements IGroup {

    private WebClient wCg = new Connection('g').getClient();

    public int createGroup(Group group){
        ResponseEntity<String> response = wCg.post()
                .uri("/addGroup")
                .body(group,Group.class)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public Group readGroup(Long id){
        Group group = wCg.get()
                .uri("/"+ id)
                .retrieve()
                .bodyToMono(Group.class)
                .block();
        return group;
    }

    public int updateGroup(Long id, Group group) {
        ResponseEntity<String> response = wCg.put()
                .uri("/" + id)
                .body(group, Group.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response.getStatusCodeValue();
    }

    public int deleteGroup(Long id){
        ResponseEntity<String> response = wCg.delete()
                .uri("/"+id)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    //////////////////////////////
    public int addUserToGroup(Long idUser,Long idGroup){
        ResponseEntity<String> response = wCg.get()
                .uri("/"+idUser+"/"+idGroup)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }

    public int deleteUserToGroup(Long idUser,Long idGroup){
        ResponseEntity<String> response = wCg.delete()
                .uri("/"+idUser+"/"+idGroup)
                .retrieve().toEntity(String.class).block();
        return response.getStatusCodeValue();
    }
}
