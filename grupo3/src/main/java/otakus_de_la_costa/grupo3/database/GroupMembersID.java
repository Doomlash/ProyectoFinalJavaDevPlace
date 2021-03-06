package otakus_de_la_costa.grupo3.database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class GroupMembersID implements Serializable{
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_member")
    private Long userId;
}