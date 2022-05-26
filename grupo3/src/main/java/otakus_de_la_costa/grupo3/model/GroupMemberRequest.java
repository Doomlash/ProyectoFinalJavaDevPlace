package otakus_de_la_costa.grupo3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberRequest {
    private Long group;
    private Long user;
    private boolean isAdmin;
}
