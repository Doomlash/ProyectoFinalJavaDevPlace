package otakus_de_la_costa.grupo3.services.implementation;

import java.util.List;
import java.util.NoSuchElementException;

import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.request.GroupMemberRequest;
import otakus_de_la_costa.grupo3.model.request.GroupRequest;

public interface IGroupService {
	public void createGroup(GroupRequest myGroup);
	public List<Group> listAllGroups();
	public Group findGroupById(Long id);
	public boolean updateGroup(Group group) throws NoSuchElementException;
	public boolean deleteGroup(Long id);
	public void addMember(GroupMemberRequest request);
	public void deleteMember(GroupMemberRequest request);
}