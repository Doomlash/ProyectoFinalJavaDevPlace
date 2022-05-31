package group3.middleware.services.interfaces;

import group3.middleware.model.Group;
import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

public interface IGroup {
    public ResponseEntity<Integer> createG(GroupRequest group);

    public ResponseEntity<Integer> addM(GroupMemberRequest request);
    public ResponseEntity<Integer> removeM(GroupMemberRequest request);
    public ResponseEntity<Group[]> listAllG();
    public ResponseEntity<Group> readG(Long id);
    public ResponseEntity<Integer> updateG(Group group) throws NoSuchElementException;
    public ResponseEntity<Integer> deleteG(Long id);

    public ResponseEntity<Object> isAdmin(Long idG,Long idU);
    public ResponseEntity<Object> changeAdmin(Long idG,Long idU);

}
