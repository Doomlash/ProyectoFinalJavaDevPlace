package otakus_de_la_costa.grupo3.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyUser extends Messenger{
	private String username;
	private String mail;
	private String firstName;
	private String lastName;
	private String language;
	private String profileImage;
	private Date birthDate;
	private Boolean active;

    private Set<SimpleUserResponse> contacts = new HashSet<>();
    private Set<SimpleUserResponse> blocks = new HashSet<>();

    private Set<Long> groups = new HashSet<Long>();


    public void addContact(Long id, String username) {
        contacts.add(new SimpleUserResponse(id,username));
    }

    public void addBlock(Long id, String username){
        blocks.add(new SimpleUserResponse(id,username));
    }

    public void addGroup(Long groupId) {
        groups.add(groupId);
    }
}

