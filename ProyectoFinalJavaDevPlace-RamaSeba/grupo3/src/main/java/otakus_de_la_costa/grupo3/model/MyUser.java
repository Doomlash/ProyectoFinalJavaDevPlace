package otakus_de_la_costa.grupo3.model;




import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyUser {
	private Long id;
	private String username;
	private String mail;
	private String firstName;
	private String lastName;
	private String language;
	private String profileImage;
	private Boolean active;
	
    private Date birthDate;
    
}

