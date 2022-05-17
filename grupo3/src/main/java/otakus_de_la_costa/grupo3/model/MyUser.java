package otakus_de_la_costa.grupo3.model;

import java.util.ArrayList;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class MyUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String name;
	private String surname;
	private String age;
	private String language;
	private String email;
	private String avatar;
	//private ArrayList<MyUser> listContacts;
	//private ArrayList<MyUser> listGroups;
	//private ArrayList<MyUser> listBlock;
}
