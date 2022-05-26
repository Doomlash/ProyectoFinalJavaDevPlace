package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.RelationRequest;

public interface IUserService {
	public boolean createUser(MyUser myUser);
	public List<MyUser> listAllUsers();
	public MyUser findUserById(Long id);
	public MyUser updateMyUser(MyUser myUser, Long id);
	public boolean deleteUser(Long id);
    public void addContact(RelationRequest request);
    public void deleteContact(RelationRequest request);
    public void addBlock(RelationRequest request);
    public void deleteBlock(RelationRequest request);
}
