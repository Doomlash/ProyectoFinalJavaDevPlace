package otakus_de_la_costa.grupo3.reposiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.model.Messages;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {
	
}
