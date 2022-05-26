package otakus_de_la_costa.grupo3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import otakus_de_la_costa.grupo3.database.GroupJPA;

@Repository
public interface GroupRepository extends JpaRepository<GroupJPA, Long> {

    @Query(value = "INSERT INTO group_members VALUES(:group_id,:group_member,:is_admin)", nativeQuery = true)
    @Modifying
    public void addMember(@Param("group_id")Long groupId,@Param("group_member") Long groupMember,@Param("is_admin")boolean isAdmin);

    @Query(value = "DELETE FROM group_members WHERE group_id = :group_id AND group_member = :group_member", nativeQuery = true)
    @Modifying
    public void deleteMember(@Param("group_id")Long groupId,@Param("group_member") Long groupMember);
	
}
