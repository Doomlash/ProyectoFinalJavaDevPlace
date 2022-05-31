package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.middleware.model.LoginResponse;
import group3.middleware.model.request.RegisterRequest;
import group3.middleware.services.SecurityService;

@RestController
@RequestMapping("/middle/security")
public class SecurityController {
    @Autowired
    SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody RegisterRequest request){
        return securityService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(){

    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(){

    }
}
