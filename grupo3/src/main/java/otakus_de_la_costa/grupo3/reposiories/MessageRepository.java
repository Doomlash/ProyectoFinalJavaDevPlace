package otakus_de_la_costa.grupo3.reposiories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.model.MyUser;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {
	public Optional<Messages> findByDateReceive(String dataReceive);
	public Optional<Messages> findByDateSend(String dataSend);
	public Optional<Messages> findByTransmitter(MyUser transmitter);
}
