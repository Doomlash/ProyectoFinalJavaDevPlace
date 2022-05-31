package otakus_de_la_costa.grupo3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ping")
public class EmptyController {
    
    @GetMapping
    public ResponseEntity<Object> ping(){
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
