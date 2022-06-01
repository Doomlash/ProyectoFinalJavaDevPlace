package group3.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import group3.mvc.model.request.RegisterRequest;
import group3.mvc.services.MyUserDetailsService;

@Controller
public class SecurityController {
    
    @Autowired
    MyUserDetailsService udService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("request", new RegisterRequest());
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(@Validated RegisterRequest registerRequest, BindingResult result, Model model){
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Integer response = udService.createUser(registerRequest);
        System.out.println(response);
        if(response!=0){
            if(response==1||response==3){
                result.addError(new FieldError(result.getObjectName(), "username", "username taken"));
            }
            if(response==2||response==3){
                result.addError(new FieldError(result.getObjectName(), "mail", "mail taken"));
            }
            System.out.println(result.hasFieldErrors("username"));
            return "redirect:/register";
        }
        User user = (User)udService.loadUserByUsername(registerRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user.getUsername(), user.getPassword(), user.getAuthorities()));
        return "redirect:/mvc/registerSuccess";
    }
}
