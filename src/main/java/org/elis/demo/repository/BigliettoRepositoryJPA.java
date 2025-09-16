package org.elis.demo.repository;

import java.util.Optional;

import org.elis.demo.model.Biglietto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BigliettoRepositoryJPA extends JpaRepository<Biglietto, Long>{
	
	Optional<Biglietto> findByPostoAndSpettacolo_Id(String posto, Long spettacoloId);
	
}
