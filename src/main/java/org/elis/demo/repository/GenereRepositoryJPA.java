package org.elis.demo.repository;


import java.util.Optional;

import org.elis.demo.model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenereRepositoryJPA extends JpaRepository<Genere, Long>{

	Optional<Genere> findByNome(String nome);
	
}
