package otakus_de_la_costa.grupo3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelationRequest {
    private Long relationOwner;
    private Long related;
}
