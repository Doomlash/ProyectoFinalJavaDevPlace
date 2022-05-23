package otakus_de_la_costa.grupo3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Messenger {
   // @JsonBackReference
    private	Set<Messages> sent= new HashSet<>();
   // @JsonBackReference
    private Set<Messages> received= new HashSet<>();
}
