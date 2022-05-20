package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupJPA;
import otakus_de_la_costa.grupo3.repositories.GroupRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository gRepository;
	
	//GET ALL GROUPS
	
	public List<GroupJPA> findAll(){
		return gRepository.findAll();
	}
	
	//GET ONE GROUP
	
	public Optional<GroupJPA> findById(Long id){
		if(gRepository.existsById(id)) {
			return gRepository.findById(id);
		}
		return null;
	}
	
	//POST GROUP
	
	public GroupJPA save(GroupJPA group) {
		return gRepository.save(group);
	}
	
	//PUT  GROUP

	public GroupJPA update(GroupJPA group) {
		if (gRepository.existsById(group.getId())) {
			return gRepository.save(group);
		}
		return null;
	}

	// DELETE GROUP

	public boolean delete(Long id) {
		if (gRepository.existsById(id)) {
			gRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
