package otakus_de_la_costa.grupo3.repositories;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.model.MyUser;

@Repository
public interface MessageRepository extends JpaRepository<MessageJPA, Long> {
	public Optional<Messages> findByDateReceive(Date dataReceive);
	public Optional<Messages> findByDateSend(Date dataSend);
	public Optional<Messages> findBYSender(MyUser sender);
}
