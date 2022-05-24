package group3.middleware.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group extends Messenger{
    private Long id;
    private String name;
    private String description;
    private Boolean active;
}
