package otakus_de_la_costa.grupo3.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.sql.Delete;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "my_users")
@Data
@NoArgsConstructor
public class MyUserJPA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "mail", nullable = false, unique = true)
	private String mail;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "language")
	private String language;

	@Column(name = "profile_image", nullable = false)
	private String profileImage;

	@Column(name = "birth_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column(name = "active")
	private Boolean active;

	@OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL)
	private Set<ContactJPA> contacts;
	
	@OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    private Set<MessageJPA> menssages;
	
	/**/
   
}
