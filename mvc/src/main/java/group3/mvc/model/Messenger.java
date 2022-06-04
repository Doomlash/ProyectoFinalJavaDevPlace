package group3.mvc.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Messenger {
    private Long id;
    private	List<Message> sent= new LinkedList<>();
    private List<Message> received= new LinkedList<>();
}