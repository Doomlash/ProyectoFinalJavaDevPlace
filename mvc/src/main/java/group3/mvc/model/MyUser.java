package group3.mvc.model;

import group3.mvc.model.request.SimpleUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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