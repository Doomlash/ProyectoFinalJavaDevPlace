package group3.mvc.services.implementation;

import java.util.List;
import java.util.NoSuchElementException;

import group3.mvc.model.Group;
import group3.mvc.model.request.GroupMemberRequest;

public interface IGroup {
    public Integer createG(Group group);

    public Integer addM(Long idG,Long idC);
    public Integer removeM(Long idG, Long idC);
    public List<Group> listAllG();
    public Group readG(Long id);
    public Integer updateG(Group group) throws NoSuchElementException;
    public Integer deleteG(Long id);
    public Object isAdmin(Long idG, Long idU);
    public Object changeAdmin(Long idG, Long idU);

}
