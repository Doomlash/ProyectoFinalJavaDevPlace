package group3.middleware.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import group3.middleware.model.request.SimpleGroupResponse;
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


	@Override
	public String toString() {
		return "MyUser{" +
				"username='" + username + '\'' +
				", mail='" + mail + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", language='" + language + '\'' +
				", profileImage='" + profileImage + '\'' +
				", birthDate=" + birthDate +
				", contacts=" + contacts +
				", blocks=" + blocks +
				", groups=" + groups +
				'}';
	}
}