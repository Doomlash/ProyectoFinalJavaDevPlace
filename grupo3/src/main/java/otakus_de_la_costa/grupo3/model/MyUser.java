package otakus_de_la_costa.grupo3.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyUser extends Messenger{
	private String username;
	private String mail;
	private String firstName;
	private String lastName;
	private String language;
	private String profileImage;
	private Date birthDate;

    private Set<SimpleUserResponse> contacts = new HashSet<>();
    private Set<SimpleUserResponse> blocks = new HashSet<>();

    private Set<SimpleGroupResponse> groups = new HashSet<>();


    public void addContact(SimpleUserResponse user) {
        contacts.add(user);
    }

    public void addBlock(SimpleUserResponse user){
        blocks.add(user);
    }

    public void addGroup(SimpleGroupResponse group) {
        groups.add(group);
    }
    
}

