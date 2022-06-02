package group3.mvc.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Comparable<Message>{
	private Long id;
	private String content;
	private String language;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
	private Long senderId;
	private Long receiverId;

    @Override
    public int compareTo(Message m) {
        return creationDate.compareTo(m.creationDate);
    }
}