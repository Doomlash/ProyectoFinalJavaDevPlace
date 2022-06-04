package otakus_de_la_costa.grupo3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserResponse {
    private Long id;
    private String username;
    private String profileImage;
}
