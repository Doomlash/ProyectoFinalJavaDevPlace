package otakus_de_la_costa.grupo3.model;



import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Messages {
	private Long id;
	private String content;
	private String language;
    private Date creationDate;
    private Date receptionDate;
    private Date readDate;
}
