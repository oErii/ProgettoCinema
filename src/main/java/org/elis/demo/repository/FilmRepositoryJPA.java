package org.elis.demo.repository;

import java.util.Optional;

import org.elis.demo.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepositoryJPA extends JpaRepository<Film, Long>{

	Optional<Film> findByTitolo(String titolo);
	
}
