package otakus_de_la_costa.grupo3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Messenger {
    @JsonBackReference
    private List<Messages> sent;
    @JsonBackReference
    private List<Messages> received;
}
