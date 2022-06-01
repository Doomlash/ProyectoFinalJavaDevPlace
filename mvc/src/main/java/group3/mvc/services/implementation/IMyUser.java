package group3.mvc.services.implementation;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.RelationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface IMyUser {
    public Integer createU(MyUser myUser);
    public List<MyUser> listAllUsers();
    public MyUser readUById(Long id);
    public MyUser readUByUsername(String username);
    public Integer updateMyUser(MyUser myUser);
    public Integer deleteU(Long id);
    public Integer addC(MyUser user);
    public Integer removeC(Long idC);
    public Integer addB(Long idC);
    public Integer removeB(Long idC);
}
