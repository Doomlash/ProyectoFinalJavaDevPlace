package group3.mvc.services.implementation;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.RelationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface IMyUser {
    public Integer createU(MyUser myUser);
    public List<MyUser> listAllUsers();
    public MyUser readU(Long id);
    public Integer updateMyUser(MyUser myUser);
    public Integer deleteU(Long id);
    public Integer addC(RelationRequest rr);
    public Integer removeC(RelationRequest rr);
    public Integer addB(RelationRequest rr);
    public Integer removeB(RelationRequest rr);
}
