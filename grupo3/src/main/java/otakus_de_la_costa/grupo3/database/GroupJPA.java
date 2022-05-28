package otakus_de_la_costa.grupo3.database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_groups")
@Getter
@Setter
@NoArgsConstructor
public class GroupJPA extends MessengerJPA{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "group")
    private List<GroupMembersJPA> members;

}