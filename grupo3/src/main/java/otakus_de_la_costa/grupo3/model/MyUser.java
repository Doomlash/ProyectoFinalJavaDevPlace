package otakus_de_la_costa.grupo3.model;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyUser {
	private Long id;

	private Integer email;
	private String avatar;
	private String username;

	private String name;
	private String lastName;
	private Integer age;
	private String language;

	private ArrayList<MyUser> listContacts;
	private ArrayList<MyUser> listGroups;
	private ArrayList<MyUser> listBlock;
}
