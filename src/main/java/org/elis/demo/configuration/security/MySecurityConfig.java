package org.elis.demo.configuration.security;

import org.elis.demo.model.Ruolo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

	private final MySecurityFilter filter;
	
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(t -> {
				
					t.requestMatchers("/all/**").permitAll();
					t.requestMatchers("/user/**").authenticated();
					t.requestMatchers("/admin/**").hasRole(Ruolo.ROLE_ADMIN.toString());
					t.anyRequest().authenticated();
					
				}
			);
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
