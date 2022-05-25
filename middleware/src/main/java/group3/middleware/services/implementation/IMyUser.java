package group3.middleware.services.implementation;

import group3.middleware.model.MyUser;
import org.springframework.http.ResponseEntity;

public interface IMyUser {
    public int createUser(MyUser user);

    public MyUser readUser(Long id);

    public int updateUser(Long id, MyUser user);

    public int deleteUser(Long id);

    ///////////////////////////////////////////////////////////////////////////
    public int addContact(Long idUser,Long idContact);

    public int addBlock(Long idUser,Long idContact);

    public int deleteContact(Long idUser,Long idContact);

    public int deleteBlock(Long idUser,Long idContact);
}
