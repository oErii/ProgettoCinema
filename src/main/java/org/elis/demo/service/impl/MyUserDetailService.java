package org.elis.demo.service.impl;

import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.repository.UtenteRepositoryJPA;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailService {

	private final UtenteRepositoryJPA repo;
	
	public UserDetails loadUserByUsername(String email) throws NessunRisultatoException {
        return repo.findByEmail(email).orElseThrow(
            () -> new NessunRisultatoException("Utente non trovato: " + email)
        );
    }
	
}
