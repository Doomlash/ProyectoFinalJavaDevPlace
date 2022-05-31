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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.RegisterRequest;
import otakus_de_la_costa.grupo3.services.MyUserDetailsService;
import otakus_de_la_costa.grupo3.services.UserService;

@RestController
@RequestMapping("/api/token")
public class TokenController {

	@Autowired
	JwtEncoder encoder;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // static boolean init = false;

	@PostMapping()
	public String token(Authentication authentication) {
        // userDetailsService.createUser(new User("user",passwordEncoder.encode("password"),new LinkedList()));
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
                                            .password(passwordEncoder.encode(request.getPassword()))
                                            .authorities("USER")
                                            .build());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }


    }

}