package otakus_de_la_costa.grupo3.services;

import static otakus_de_la_costa.grupo3.model.Constants.IDS_NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.NOT_MEMBER;
import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupJPA;
import otakus_de_la_costa.grupo3.database.GroupMembersJPA;
import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.GroupMemberRequest;
import otakus_de_la_costa.grupo3.model.GroupRequest;
import otakus_de_la_costa.grupo3.repositories.GroupRepository;

@Service
public class GroupService implements IGroupService{
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
        if(gRepo.findById(id).isPresent())
        {
            gRepo.deleteById(id);
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

    @Override
    public Boolean isAdmin(Long group, Long user) {
        if(gRepo.findById(group).isEmpty()){
            throw new NoSuchElementException();
        } else{
            Boolean result = gRepo.isAdmin(group, user);
            return result;
        }
    }

    @Override
    @Transactional
    public Integer changeAdmin(Long group, Long user) {
        if(gRepo.findById(group).isEmpty()){
            return IDS_NOT_FOUND;
        } 
        if(gRepo.isAdmin(group, user)==null){
            return NOT_MEMBER;
        }
        gRepo.changeAdmin(group, user);
        return OK;
    }
}