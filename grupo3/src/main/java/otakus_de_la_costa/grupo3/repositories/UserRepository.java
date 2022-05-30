package otakus_de_la_costa.grupo3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.MyUserJPA;

@Repository
public interface UserRepository extends JpaRepository<MyUserJPA, Long> {
	public Optional<MyUserJPA> findByUsername(String username);
	public Optional<MyUserJPA> findByMail(String mail);
	public Optional<MyUserJPA> findByUsernameOrMail(String username,String mail);


    @Query(value = "INSERT INTO contacts VALUES(:contactOwner,:contacted)", nativeQuery = true)
    @Modifying
    public void addContact(@Param("contactOwner")Long contactOwner,@Param("contacted") Long contacted);
    
    @Query(value = "DELETE FROM contacts WHERE contact_owner = :contactOwner AND contacted = :contacted", nativeQuery = true)
    @Modifying
    public void deleteContact(@Param("contactOwner")Long contactOwner,@Param("contacted") Long contacted);
    
    @Query(value = "INSERT INTO blocks VALUES(:blockOwner,:blocked)", nativeQuery = true)
    @Modifying
    public void addBlock(@Param("blockOwner")Long blockOwner,@Param("blocked") Long blocked);

    @Query(value = "DELETE FROM blovalcks WHERE block_owner = :blockOwner AND blocked = :blocked", nativeQuery = true)
    @Modifying
    public void deleteBlock(@Param("blockOwner")Long blockOwner,@Param("blocked") Long blocked);

    
	
    
}
