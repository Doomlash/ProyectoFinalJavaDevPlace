package group3.mvc.model;

import java.util.*;

import group3.mvc.model.request.SimpleGroupResponse;
import group3.mvc.model.request.SimpleUserResponse;
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

	private List<SimpleGroupResponse> groups = new ArrayList<>();


	public void addContact(SimpleUserResponse user) {
        contacts.add(user);
    }

    public void addBlock(SimpleUserResponse user){
        blocks.add(user);
    }

    public void addGroup(SimpleGroupResponse group) {
        groups.add(group);
    }

    public void addMessage(Message message){this.getSent().add(message);}

    @Override
    public String toString() {
        return "MyUser [birthDate=" + birthDate + ", blocks=" + blocks + ", contacts=" + contacts + ", firstName="
                + firstName + ", groups=" + groups + ", language=" + language + ", lastName=" + lastName + ", mail="
                + mail + ", profileImage=" + profileImage + ", username=" + username + "]";
    }
}