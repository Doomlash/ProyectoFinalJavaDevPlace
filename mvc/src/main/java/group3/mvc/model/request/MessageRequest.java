package group3.mvc.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageRequest {
    private String content;
    private String language;
    private Long senderId;
    private Long receiverId;
}