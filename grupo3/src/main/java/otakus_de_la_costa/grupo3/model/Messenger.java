package otakus_de_la_costa.grupo3.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Messenger {
    private Long id;
    private	Set<Message> sent= new HashSet<>();
    private Set<Message> received= new HashSet<>();
}
