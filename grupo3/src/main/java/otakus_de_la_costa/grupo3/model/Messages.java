package otakus_de_la_costa.grupo3.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
	private Long id;
	private String content;
	private String messageLanguage;
	private MyUser sender;
	private MyUser receiver;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
}
