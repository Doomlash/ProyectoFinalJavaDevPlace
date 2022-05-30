package group3.mvc.services;

import org.springframework.stereotype.Service;

@Service
public class MiddleService {

    MiddleConection mc = new MiddleConection();

    public Object getRatio(String id) {
        return mc.getRatio(id);
    }
    
}