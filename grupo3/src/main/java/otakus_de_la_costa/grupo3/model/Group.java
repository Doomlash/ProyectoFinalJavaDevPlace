package otakus_de_la_costa.grupo3.model;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends Messenger{
    private String name;
    private String description;
    private Boolean active;

    private Set<SimpleUserResponse> group_members = new HashSet<>();

    public void addMember(Long id, String username){
        group_members.add(new SimpleUserResponse(id, username));
    }
}
