package otakus_de_la_costa.grupo3.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group extends Messenger{
    private String name;
    private String description;
    private Boolean active;

    private Set<MyUser> group_members;
}
