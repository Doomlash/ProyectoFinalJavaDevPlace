package otakus_de_la_costa.grupo3.services.implementation;

import java.util.List;

import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.request.RelationRequest;

public interface IUserService {
	public int createUser(MyUser myUser);
	public List<MyUser> listAllUsers();
	public MyUser findUserById(Long id);
	public int updateMyUser(MyUser myUser);
	public void deleteUser(Long id);
	public void addContact(RelationRequest request);
	public int deleteContact(RelationRequest request);
	public void addBlock(RelationRequest request);
	public int deleteBlock(RelationRequest request);
}