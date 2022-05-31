package otakus_de_la_costa.grupo3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.AuthenticationJPA;

@Repository
public interface LogInRepository extends JpaRepository<AuthenticationJPA,String>{

    @Query(value = "CALL change_password(:username, :old_password, :new_password)", nativeQuery = true)
    void updatePassword(@Param("username") String username, 
                            @Param("old_password") String password, 
                            @Param("new_password") String newPassword);
    
}
