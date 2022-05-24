package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Group;

public interface IGroupService {
	public Group createGroup(Group myGroup);
	public List<Group> listAllGroups();
	public Group findGroupById(Long id);
	public Group updateGroup(Group group, Long id) ;
	public boolean deleteGroup(Long id);
}
