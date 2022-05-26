package otakus_de_la_costa.grupo3.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MessageJPA;

@Repository
public interface MessageRepository extends JpaRepository<MessageJPA, Long> {
	@Query(value = "CALL message_received(:id)", nativeQuery = true)
	@Modifying
	void receiveMessage(@Param("id")Long id);

	@Query(value = "CALL message_read(:id)", nativeQuery = true)
	@Modifying
	void readMessage(@Param("id")Long id);

}