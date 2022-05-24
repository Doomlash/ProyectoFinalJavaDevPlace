package otakus_de_la_costa.grupo3.repositories;

import otakus_de_la_costa.grupo3.database.GroupJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupJPA, Long> {
}
