package group3.middleware.services;

import org.springframework.stereotype.Service;

import group3.middleware.services.connection.TenorConnection;

@Service
public class TenorService {
    
    private TenorConnection tc = new TenorConnection();
    
    
    public double get(String id){
        return tc.get(id);
    }
}
