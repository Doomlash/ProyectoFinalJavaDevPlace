package otakus_de_la_costa.grupo3.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
	private Long id;
	private String messageLanguage;
	@JsonManagedReference
	private Messenger sender;
	@JsonManagedReference
	private Messenger receiver;
	private String content;
	private String language;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
}
