package org.elis.demo.repository;

import java.util.Optional;

import org.elis.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepositoryJPA extends JpaRepository<Utente, Long>{
	
	Optional<Utente> findByEmail(String email);	
	
}
