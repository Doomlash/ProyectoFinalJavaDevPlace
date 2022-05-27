package group3.mvc.services.implementation;

import group3.mvc.model.Group;
import group3.mvc.model.GroupMemberRequest;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

public interface IGroup {
    public ResponseEntity<String> createG(Group group);

    public ResponseEntity<String> addM(GroupMemberRequest request);
    public ResponseEntity<String> removeM(GroupMemberRequest request);
    public ResponseEntity<Group[]> listAllG();
    public ResponseEntity<Group> readG(Long id);
    public ResponseEntity<String> updateG(Group group) throws NoSuchElementException;
    public ResponseEntity<String> deleteG(Long id);

}
