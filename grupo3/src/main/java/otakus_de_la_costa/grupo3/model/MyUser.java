package otakus_de_la_costa.grupo3.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser extends Messenger{
	private Long id;
	private String username;
	private String mail;
	private String firstName;
	private String lastName;
	private String language;
	private String profileImage;
	private Date birthDate;
	private Boolean active;
	private List<MyUser> contacts;
	private List<MyUser> blocks;
}
