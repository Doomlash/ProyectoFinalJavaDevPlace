package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.NoSuchElementException;

import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.GroupMemberRequest;
import otakus_de_la_costa.grupo3.model.GroupRequest;

public interface IGroupService {
	public void createGroup(GroupRequest myGroup);
	public List<Group> listAllGroups();
	public Group findGroupById(Long id);
	public boolean updateGroup(Group group) throws NoSuchElementException;
	public boolean deleteGroup(Long id);
    public void addMember(GroupMemberRequest request);
    public void deleteMember(GroupMemberRequest request);
    public Boolean isAdmin(Long group, Long user);
    public Integer changeAdmin(Long group, Long user);
}
