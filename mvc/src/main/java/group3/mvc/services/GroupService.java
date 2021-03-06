package group3.mvc.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.SimpleMemberResponse;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import group3.mvc.model.Group;
import group3.mvc.model.UserHolder;
import group3.mvc.model.request.GroupMemberRequest;
import group3.mvc.model.request.GroupRequest;
import group3.mvc.model.request.SimpleGroupResponse;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IGroup;
import reactor.core.publisher.Mono;


@Service
public class GroupService implements IGroup {

    private WebClient wCg = Connection.getClient();

    @Autowired
    IMyUser iMU;


    public Integer createG(Group group){
        GroupRequest gr = new GroupRequest(group.getName(),group.getDescription(), UserHolder.getCurrentUser().getId());
        try {
            ResponseEntity<Integer> response = wCg.post()
                    .uri("/groups")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .body(Mono.just(gr),GroupRequest.class)
                    .retrieve()
                    .toEntity(Integer.class)
                    .block();

            UserHolder.getCurrentUser().addGroup(retrieveGroup(group.getName()));

            return response.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return createG(group);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public List<Group> listAllG(){
        try {
            ResponseEntity<Group[]> rLAg = wCg.get()
                    .uri("/groups")
                    .header("Authorization", "Bearer "+ Connection.getToken())
                    .retrieve()
                    .toEntity(Group[].class)
                    .block();
            List<Group> rLAgl = Arrays.asList(rLAg.getBody());
            return rLAgl;
        }catch (WebClientResponseException e){
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return listAllG();
            }
            return Collections.emptyList();
        }
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
                return readG(id);
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

            updateGroup(group);

            return ug.getBody();
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return updateG(group);
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

            deleteGtoHolder(id);

            return dg.getBody();
        }catch (WebClientResponseException e){
            System.out.println("FALLO");
            if(e.getStatusCode().compareTo(HttpStatus.INTERNAL_SERVER_ERROR) ==0){
                return ResponseEntity.internalServerError().build().getStatusCodeValue();
            }
            if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return deleteG(id);
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
                return isAdmin(idG,idU);
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
                return changeAdmin(idG,idU);
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
            }if(e.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0){
                Connection.generateToken();
                return addM(idG,idC);
            }

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    public Integer removeM(Long idG, Long idM) {
        GroupMemberRequest gmr = retrieveMember(idG,idM);
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
                return removeM(idG,idM);
            }
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body((Integer.valueOf(e.getResponseBodyAsString()))).getBody();
        }
    }

    /**Herramientas Auxiliares*/
    public void updateGroup(Group group){
//       SimpleGroupResponse sgr = retrieveGroup(group.getName());
       for(SimpleGroupResponse sgrE : UserHolder.getCurrentUser().getGroups()){
           if(sgrE.getId() == group.getId()){
               if(!group.getName().isEmpty()){
                   sgrE.setName(group.getName());

               }
               if(!group.getDescription().isEmpty()){
                   sgrE.setDescription(group.getDescription());
               }
               break;
           }
       }
    }

    public SimpleGroupResponse retrieveGroup(String gName){
        MyUser user = iMU.readUByUsername(UserHolder.getCurrentUser().getUsername());
        for(SimpleGroupResponse sgr : user.getGroups()){
            if(gName.compareTo(sgr.getName()) ==0){
                return sgr;
            }
        }
        return null;
    }

    public void deleteGtoHolder(Long idG){
        List<SimpleGroupResponse> list = UserHolder.getCurrentUser().getGroups();
        for (int i = 0; i < list.size() ; i++) {
            if(list.get(i).getId() == idG){
                list.remove(i);
                break;
            }
        }
    }

    public GroupMemberRequest retrieveMember(Long idG,Long idM){
        Group group = readG(idG);
        GroupMemberRequest gmr = new GroupMemberRequest();
        for(SimpleMemberResponse smr: group.getGroup_members()){
            if(smr.getId()==idM){
                if(smr.isAdmin()){
                    gmr =  new GroupMemberRequest(idG,idM,true);
                }else{
                    gmr = new GroupMemberRequest(idG,idM,false);
                }
            }
        }
        return gmr;
    }

}
