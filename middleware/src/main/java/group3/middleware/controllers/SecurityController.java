package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.middleware.services.SecurityService;

@RestController
@RequestMapping("/middle/register")
public class SecurityController {
    @Autowired
    SecurityService securityService;

    @GetMapping()
    public ResponseEntity<Integer> register(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
