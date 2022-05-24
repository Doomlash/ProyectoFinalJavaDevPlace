package otakus_de_la_costa.grupo3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUserJPA, Long> {
	public Optional<MyUser> findByUsername(String username);

	public Optional<MyUser> findByEmail(String email);

	public Optional<MyUser> findByUsernameOrEmail(String username, String email);
}