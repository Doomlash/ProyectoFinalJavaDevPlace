package otakus_de_la_costa.grupo3.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import otakus_de_la_costa.grupo3.database.MessageJPA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message  {
    public Message(MessageJPA m) {
        id = m.getId();
        content = m.getContent();
        language = m.getLanguage();
        creationDate = m.getCreationDate();
        receptionDate = m.getReceptionDate();
        readDate = m.getReadDate();
        senderId = m.getSender().getId();
        receiverId = m.getReceiver().getId();
    }
    private Long id;
	private String content;
	private String language;
	private Date creationDate;
	private Date receptionDate;
	private Date readDate;
    private Long senderId;
    private Long receiverId;
}
