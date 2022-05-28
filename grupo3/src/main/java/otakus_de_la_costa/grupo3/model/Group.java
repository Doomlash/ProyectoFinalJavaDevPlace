package otakus_de_la_costa.grupo3.model;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otakus_de_la_costa.grupo3.model.request.SimpleMemberResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends Messenger{
    private String name;
    private String description;

    private Set<SimpleMemberResponse> group_members = new HashSet<>();

    public void addMember(Long id, String username, boolean admin){
        group_members.add(new SimpleMemberResponse(id, username,admin));
    }
}