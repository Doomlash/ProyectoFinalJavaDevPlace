package otakus_de_la_costa.grupo3.services;

import java.util.List;


import otakus_de_la_costa.grupo3.model.MyUser;

public interface IUserService {
	public MyUser createUser(MyUser myUser);
	public List<MyUser> listAllUsers();
	public MyUser findUserById(Long id);
	public MyUser updateMyUser(MyUser myUser, Long id);
	public boolean deleteUser(Long id);
}
