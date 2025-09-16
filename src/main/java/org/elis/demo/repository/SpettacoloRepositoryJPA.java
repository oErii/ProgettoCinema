package org.elis.demo.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.elis.demo.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpettacoloRepositoryJPA extends JpaRepository<Spettacolo, Long>{
	
    Optional<Spettacolo> findBySala_IdAndDataOra(Long salaId, LocalDateTime dataOra);
    
    List<Spettacolo> findByFilm_IdOrderByDataOraAsc(Long filmId);

    List<Spettacolo> findByDataOraBetweenOrderByDataOraAsc(LocalDateTime start, LocalDateTime end);

}
