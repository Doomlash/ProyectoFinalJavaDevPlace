package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import group3.middleware.services.TenorService;

@RestController
public class TenorController {
    @Autowired
    private TenorService ts;

    @GetMapping("/gifRatio/{id}")
    public double get(@PathVariable("id") String id){
        return ts.get(id);
    }
}
