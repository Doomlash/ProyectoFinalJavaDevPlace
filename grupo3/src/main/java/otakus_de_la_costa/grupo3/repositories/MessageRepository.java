package otakus_de_la_costa.grupo3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MessageJPA;

@Repository
public interface MessageRepository extends JpaRepository<MessageJPA, Long> {

}
