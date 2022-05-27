package group3.mvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberRequest {
    private Long group;
    private Long user;
    private boolean isAdmin;
}