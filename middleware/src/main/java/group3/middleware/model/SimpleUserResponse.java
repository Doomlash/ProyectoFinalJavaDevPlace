package group3.middleware.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleUserResponse {
    private Long id;
    private String username;
    public SimpleUserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}