package group3.mvc.services.implementation;

import java.util.List;

import group3.mvc.model.MyUser;


public interface IMyUser {
    public List<MyUser> listAllUsers();
    public MyUser readUById(Long id);
    public MyUser readUByUsername(String username);
    public Integer updateMyUser(MyUser myUser);
    public Integer deleteU(Long id);
    public Integer addC(MyUser user);
    public Integer removeC(Long idC);
    public Integer addB(MyUser user);
    public Integer removeB(Long idC);
}
