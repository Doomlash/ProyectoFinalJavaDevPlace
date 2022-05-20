package otakus_de_la_costa.grupo3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.GroupJPA;

@Repository
public interface GroupRepository extends JpaRepository<GroupJPA, Long> {

}
