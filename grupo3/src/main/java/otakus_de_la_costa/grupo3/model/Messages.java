package otakus_de_la_costa.grupo3.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="messages")
@Data
@NoArgsConstructor
public class Messages {
	@Id
	private Long id;

	private String content;
	private String messageLanguage;

	private String transmitter;
	private String receiver;

	private String dateSend;
	private String dateReceive;
	private String dateRead;
}
