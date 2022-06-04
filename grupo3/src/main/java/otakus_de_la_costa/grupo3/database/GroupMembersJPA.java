package otakus_de_la_costa.grupo3.database;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@NoArgsConstructor
public class GroupMembersJPA {

    @EmbeddedId
    private GroupMembersID id;

    @Column(name = "isAdmin")
    private boolean admin;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupJPA group;

    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name = "group_member")
    private MyUserJPA user;

}