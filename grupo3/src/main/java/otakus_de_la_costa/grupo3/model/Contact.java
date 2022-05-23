package otakus_de_la_costa.grupo3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {
	private Long id;
	private String username;
	private String email;
	private boolean isBlock;
}
