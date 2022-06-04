package group3.mvc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import group3.mvc.services.MyUserDetailsService;    

@Configuration
public class SpringSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
            .authorizeHttpRequests((authorize) -> authorize
                    .antMatchers("/mvc/**").authenticated()
                    .anyRequest().permitAll()
            )
            .formLogin(
                form -> form
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/mvc/loginSuccess")
            )
            .csrf((csrf) -> csrf.disable());
		return http.build();
	}

    @Bean
    public MyUserDetailsService customUserDetailsService() {
        return new MyUserDetailsService();
    }

}
