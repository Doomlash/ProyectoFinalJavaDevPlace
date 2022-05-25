package group3.middleware.services.implementation;

import group3.middleware.model.Group;

public interface IGroup {
    public int createGroup(Group group);

    public Group readGroup(Long id);

    public int updateGroup(Long id, Group group);

    public int deleteGroup(Long id);

    //////////////////////////////
    public int addUserToGroup(Long idUser,Long idGroup);

    public int deleteUserToGroup(Long idUser,Long idGroup);
}
