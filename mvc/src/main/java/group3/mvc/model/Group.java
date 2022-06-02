package group3.mvc.model;

import group3.mvc.model.request.SimpleMemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", group_members=" + group_members +
                '}';
    }
}