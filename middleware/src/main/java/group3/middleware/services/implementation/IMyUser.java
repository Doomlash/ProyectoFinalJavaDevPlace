package group3.middleware.services.implementation;

import group3.middleware.model.MyUser;
import group3.middleware.model.RelationRequest;
import org.springframework.http.ResponseEntity;


public interface IMyUser {
    public ResponseEntity<String> createU(MyUser myUser);
    public ResponseEntity<MyUser[]> listAllUsers();
    public ResponseEntity<MyUser> readU(Long id);
    public ResponseEntity<String> updateMyUser(MyUser myUser);
    public ResponseEntity<String> deleteU(Long id);
    public ResponseEntity<String> addC(RelationRequest rr);
    public ResponseEntity<String> removeC(RelationRequest rr);
    public ResponseEntity<String> addB(RelationRequest rr);
    public ResponseEntity<String> removeB(RelationRequest rr);
}
