package org.elis.demo.configuration.security;

import java.io.IOException;

import org.elis.demo.configuration.JWTUtilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
@Component
public class MySecurityFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JWTUtilities jwtUtilities;
	@Qualifier("handlerExceptionResolver")
	private final HandlerExceptionResolver exceptionResolver;
	
	public MySecurityFilter(UserDetailsService userDetailsService, JWTUtilities jwtUtilities,
			@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtUtilities = jwtUtilities;
		this.exceptionResolver = exceptionResolver;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") || securityContext.getAuthentication() != null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authorizationHeader.substring(7);
		String username;
		try {
			
			username = jwtUtilities.getSubject(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(userDetails == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			upat.setDetails(new WebAuthenticationDetailsSource());
			securityContext.setAuthentication(upat);
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			exceptionResolver.resolveException(request, response, null, e);
			filterChain.doFilter(request, response);
		}
		
	}
}
