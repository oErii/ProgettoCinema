package org.elis.demo.configuration.security;

import java.io.IOException;

import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.impl.MyUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
@Component
public class MySecurityFilter extends OncePerRequestFilter {

	private final MyUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		
		if(username == null || password == null || securityContext.getAuthentication() != null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		UserDetails userDetails;
		userDetails = userDetailsService.loadUserByUsername(username);
		
		if(userDetails == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		upat.setDetails(new WebAuthenticationDetailsSource());
		securityContext.setAuthentication(upat);
		filterChain.doFilter(request, response);
		
	}
}
