package otakus_de_la_costa.grupo3.controllers;

import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.LoginResponse;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.RegisterRequest;
import otakus_de_la_costa.grupo3.services.MyUserDetailsService;
import otakus_de_la_costa.grupo3.services.UserService;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

	@Autowired
	JwtEncoder encoder;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // static boolean init = false;

    private String generateToken(Authentication authentication){
        Instant now = Instant.now();
		long expiry = 3600L;
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

	@PostMapping("/login/{username}")
	public LoginResponse login(@PathVariable("username") String username, Authentication authentication) {
		String token = generateToken(authentication);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(userService.findByUsername(username));
		return response;
	}

    @PostMapping("/token")
    public String getToken(Authentication authentication){
        return generateToken(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody RegisterRequest request){
        MyUser u = new MyUser();
		u.setUsername(request.getUsername());
        u.setMail(request.getMail());
        u.setFirstName(request.getFirstName());
        u.setLastName(request.getLastName());
        u.setLanguage(request.getLanguage());
        u.setBirthDate(request.getBirthDate());
        u.setProfileImage(request.getProfileImage());
        Integer response = userService.createUser(u);
        if(response == OK){
            userDetailsService.createUser(User.builder()
                                            .username(request.getUsername())
                                            .password(request.getPassword())
                                            .authorities("USER")
                                            .build());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/credentials/{username}")
    public ResponseEntity<Object> getCredentials(@PathVariable("username") String username){
        try{
            return new ResponseEntity<>(userDetailsService.loadUserByUsername(username),HttpStatus.OK);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}