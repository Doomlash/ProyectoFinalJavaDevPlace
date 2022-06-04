package group3.middleware.services.interfaces;

import group3.middleware.model.MyUser;
import group3.middleware.model.request.RelationRequest;
import org.springframework.http.ResponseEntity;


public interface IMyUser {
    public ResponseEntity<MyUser[]> listAllUsers();
    public ResponseEntity<MyUser> readUById(Long id);
    public ResponseEntity<MyUser> readUByUsername(String username);
    public ResponseEntity<Integer> updateMyUser(MyUser myUser);
    public ResponseEntity<Integer> deleteU(Long id);
    public ResponseEntity<Integer> addC(RelationRequest rr);
    public ResponseEntity<Integer> removeC(RelationRequest rr);
    public ResponseEntity<Integer> addB(RelationRequest rr);
    public ResponseEntity<Integer> removeB(RelationRequest rr);
}
