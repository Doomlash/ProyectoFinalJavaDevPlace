package group3.middleware.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMemberResponse{
    private Long id;
    private String username;
    private boolean admin;
}