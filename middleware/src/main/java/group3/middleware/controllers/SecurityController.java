package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login/{username}")
    public ResponseEntity<Object> login(@PathVariable("username") String username){
        System.out.println(username);
        return new ResponseEntity<>(securityService.login(username),HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<Object> getToken(){
        return new ResponseEntity<>(securityService.getToken(),HttpStatus.OK);
    }

    @GetMapping("/credentials/{username}")
    public ResponseEntity<Object> getCredentials(@PathVariable("username") String username){
        try{
            return new ResponseEntity<>(securityService.getCredentials(username),HttpStatus.OK);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
