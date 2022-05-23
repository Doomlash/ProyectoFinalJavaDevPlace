package group3.middleware.services;

import org.springframework.stereotype.Service;

import group3.middleware.services.connections.TenorConnection;

@Service
public class TenorService {
    
    private TenorConnection tc = new TenorConnection();
    
    
    public double get(String id){
        return tc.get(id);
    }
}
