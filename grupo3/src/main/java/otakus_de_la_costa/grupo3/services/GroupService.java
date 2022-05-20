package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupJPA;
import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.repositories.GroupRepository;

@Service
public class GroupService implements IGroupService{
	@Autowired
	private GroupRepository gRepo;
	
	//GUARDADO DEL OBJETO JSON A JPA
		@Override
		public Group createGroup(Group myGroup) {
			GroupJPA groupJPA= mapearGroupJPA(myGroup);
			GroupJPA  newGroupJPA = gRepo.save(groupJPA);
			Group response = mapearGroup(newGroupJPA);
			return response;
		}
		
		private Group mapearGroup(GroupJPA myGroupJPA) {
			Group group = new Group();
			group.setId(myGroupJPA.getId());
			group.setName(myGroupJPA.getName());
			group.setActive(myGroupJPA.getActive());
			group.setDescription(myGroupJPA.getDescription());
			return group;
		}
		

		
		private GroupJPA mapearGroupJPA(Group group) {
			GroupJPA groupJPA = new GroupJPA();
			groupJPA.setName(group.getName());
			groupJPA.setActive(group.getActive());
			groupJPA.setDescription(group.getDescription());
			return groupJPA;
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
		public Group updateGroup(Group group, Long id) {
			Optional<GroupJPA> groupJPA = gRepo.findById(id);
			groupJPA.get().setName(group.getName());
			groupJPA.get().setActive(group.getActive());
			groupJPA.get().setDescription(group.getDescription());
			return mapearGroup(groupJPA.get());
		}
		
		@Override
		public boolean deleteGroup(Long id) {
			Optional<GroupJPA> group=gRepo.findById(id);
			if(group.isPresent())
			{
				gRepo.delete(group.get());
				return true;
			}
			return false;
		}
}