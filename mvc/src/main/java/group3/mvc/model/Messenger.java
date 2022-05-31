package group3.mvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Messenger {
    private Long id;
    private	Set<Message> sent= new HashSet<>();
    private Set<Message> received= new HashSet<>();
}