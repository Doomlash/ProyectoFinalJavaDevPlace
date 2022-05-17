package otakus_de_la_costa.grupo3.model;

import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Messages {
	@Id
	private Long id;

	private String content;
	private String messageLanguage;

	private MyUser transmitter;
	private MyUser receiver;

	private String dateSend;
	private String dateReceive;
	private String dateRead;
}
