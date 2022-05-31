package group3.middleware.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberRequest {
    private Long group;
    private Long user;
    private boolean admin;
}