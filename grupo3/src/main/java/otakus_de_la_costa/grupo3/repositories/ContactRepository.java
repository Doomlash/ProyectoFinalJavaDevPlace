package otakus_de_la_costa.grupo3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.ContactJPA;

@Repository
public interface ContactRepository extends JpaRepository<ContactJPA, Long> {
	public List<ContactJPA> findByMyUserId(Long contactId);
}
