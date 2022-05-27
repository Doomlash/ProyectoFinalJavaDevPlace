package group3.mvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelationRequest {
    private Long relationOwner;
    private Long related;
}