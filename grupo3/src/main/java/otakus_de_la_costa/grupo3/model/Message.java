package otakus_de_la_costa.grupo3.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message  {
	private Long id;
	private String content;
	private String language;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
	private Long senderId;
	private Long receiverId;
}