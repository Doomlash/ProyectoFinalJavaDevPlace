package group3.middleware.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Messenger {
    private List<Messages> sent;
    private List<Messages> received;
}
