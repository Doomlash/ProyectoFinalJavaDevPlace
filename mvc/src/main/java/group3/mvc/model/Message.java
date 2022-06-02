package group3.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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