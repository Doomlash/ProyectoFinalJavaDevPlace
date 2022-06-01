package otakus_de_la_costa.grupo3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGroupResponse {
    private Long id;
    private String name;
    private String description;
}
