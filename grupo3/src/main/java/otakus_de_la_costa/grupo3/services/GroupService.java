package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupJPA;
import otakus_de_la_costa.grupo3.database.GroupMembersJPA;
import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.request.GroupMemberRequest;
import otakus_de_la_costa.grupo3.model.request.GroupRequest;
import otakus_de_la_costa.grupo3.repositories.GroupRepository;
import otakus_de_la_costa.grupo3.services.implementation.IGroupService;

@Service
public class GroupService implements IGroupService {
	@Autowired
	private GroupRepository gRepo;

	private Group mapearGroup(GroupJPA myGroupJPA) {
		Group group = new Group();
		group.setId(myGroupJPA.getId());
		group.setName(myGroupJPA.getName());
		group.setDescription(myGroupJPA.getDescription());
		for ( GroupMembersJPA member: myGroupJPA.getMembers()) {
			group.addMember(member.getUser().getId(), member.getUser().getUsername(),member.isAdmin());
		}
		return group;
	}

	private GroupJPA mapearGroupJPA(Group group) {
		GroupJPA groupJPA = new GroupJPA();
		groupJPA.setName(group.getName());
		groupJPA.setDescription(group.getDescription());
		return groupJPA;
	}

	//GUARDADO DEL OBJETO JSON A JPA
	@Override
	@Transactional
	public void createGroup(GroupRequest myGroup) {
		Group g = new Group();
		g.setDescription(myGroup.getDescription());
		g.setName(myGroup.getName());
		GroupJPA groupJPA= mapearGroupJPA(g);
		groupJPA = gRepo.save(groupJPA);
		addMember(new GroupMemberRequest(groupJPA.getId(), myGroup.getUserId(), true));
	}

	@Override
	public List<Group> listAllGroups() {
		List<GroupJPA> group = gRepo.findAll();
		return group.stream().map(groupJPA -> mapearGroup(groupJPA)).collect(Collectors.toList());
	}

	@Override
	public Group findGroupById(Long id) {
		Optional<GroupJPA> groupJPA= gRepo.findById(id);
		if(groupJPA.isPresent()) {
			return mapearGroup(groupJPA.get());
		}
		return null;
	}
	@Override
	public boolean updateGroup(Group group){
		Optional<GroupJPA> optional = gRepo.findById(group.getId());
		if(optional.isEmpty()){
			return false;
		}
		GroupJPA groupJPA = optional.get();
		if(group.getName()!=null){
			groupJPA.setName(group.getName());
		}
		if(group.getDescription()!=null){
			groupJPA.setDescription(group.getDescription());
		}
		gRepo.save(groupJPA);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteGroup(Long id) {
		Optional<GroupJPA> group=gRepo.findById(id);
		if(group.isPresent())
		{
			gRepo.delete(group.get());
			return true;
		}
		return false;

	}

	@Override
	@Transactional
	public void addMember(GroupMemberRequest request){
		gRepo.addMember(request.getGroup(), request.getUser(), request.isAdmin());

	}

	@Override
	@Transactional
	public void deleteMember(GroupMemberRequest request) {
		gRepo.deleteMember(request.getGroup(), request.getUser());
	}
}