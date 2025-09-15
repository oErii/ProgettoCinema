package org.elis.demo.repository;

import java.util.Optional;

import org.elis.demo.model.Attore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttoreRepositoryJPA extends JpaRepository<Attore, Long>{

	Optional<Attore> findByNomeAndCognome(String nome, String cognome);
	
}
