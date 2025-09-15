package org.elis.demo.service.impl;

import org.elis.demo.repository.UtenteRepositoryJPA;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService{

	private final UtenteRepositoryJPA repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("Utente non trovato: " + email)
        );
    }
	
}
