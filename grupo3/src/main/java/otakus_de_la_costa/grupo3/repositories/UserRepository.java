package otakus_de_la_costa.grupo3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MyUserJPA;

@Repository
public interface UserRepository extends JpaRepository<MyUserJPA, Long> {
	public Optional<MyUserJPA> findByUsername(String username);
	public Optional<MyUserJPA> findByMail(String mail);
	public Optional<MyUserJPA> findByUsernameOrMail(String username,String mail);
	
}
