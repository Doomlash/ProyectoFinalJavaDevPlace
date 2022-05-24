package group3.middleware.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
	private Long id;
	private String messageLanguage;
	private Messenger sender;
	private Messenger receiver;
	private String content;
	private String language;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
}
