package otakus_de_la_costa.grupo3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageRequest {
    private String content;
	private String language;
    private Long senderId;
    private Long receiverId;
}
