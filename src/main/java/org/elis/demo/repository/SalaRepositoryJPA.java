package org.elis.demo.repository;

import java.util.Optional;

import org.elis.demo.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepositoryJPA extends JpaRepository<Sala, Long>{

	Optional<Sala> findByNome(String nome);

	
}
