package org.elis.demo.controller;

import java.util.List;
import java.util.Optional;

import org.elis.demo.model.Biglietto;
import org.elis.demo.model.Spettacolo;
import org.elis.demo.model.Utente;
import org.elis.demo.repository.BigliettoRepositoryJPA;
import org.elis.demo.repository.SpettacoloRepositoryJPA;
import org.elis.demo.repository.UtenteRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BigliettoController {

	@Autowired
    private BigliettoRepositoryJPA bigliettoRepository;
	@Autowired
	private SpettacoloRepositoryJPA spettacoloRepository;
	@Autowired
	private UtenteRepositoryJPA utenteRepository;
	
	@PostMapping("/admin/cBiglietto")
	public ResponseEntity<String> aggiungiBiglietto(@RequestBody Biglietto nuovoBiglietto) {
	    
	    // Controlla se lo spettacolo esiste
	    if(nuovoBiglietto.getSpettacolo() != null && nuovoBiglietto.getSpettacolo().getId() != null) {
	        Optional<Spettacolo> spettacoloEsistente = spettacoloRepository.findById(nuovoBiglietto.getSpettacolo().getId());
	        if(spettacoloEsistente.isEmpty()) {
	            return ResponseEntity.badRequest().body("Spettacolo non trovato con ID: " + nuovoBiglietto.getSpettacolo().getId());
	        }
	        nuovoBiglietto.setSpettacolo(spettacoloEsistente.get());
	    }
	    
	    // Controlla se l'utente esiste
	    if(nuovoBiglietto.getUtente() != null && nuovoBiglietto.getUtente().getId() != null) {
	        Optional<Utente> utenteEsistente = utenteRepository.findById(nuovoBiglietto.getUtente().getId());
	        if(utenteEsistente.isEmpty()) {
	            return ResponseEntity.badRequest().body("Utente non trovato con ID: " + nuovoBiglietto.getUtente().getId());
	        }
	        nuovoBiglietto.setUtente(utenteEsistente.get());
	    }
	    
	    // Controlla se il posto è già occupato per questo spettacolo
	    if(nuovoBiglietto.getSpettacolo() != null && nuovoBiglietto.getPosto() != null) {
	        List<Biglietto> bigliettiEsistenti = bigliettoRepository.findAll();
	        for(Biglietto biglietto : bigliettiEsistenti) {
	            if(biglietto.getSpettacolo() != null && 
	               biglietto.getSpettacolo().getId().equals(nuovoBiglietto.getSpettacolo().getId()) &&
	               biglietto.getPosto().equals(nuovoBiglietto.getPosto())) {
	                return ResponseEntity.badRequest().body("Posto già occupato per questo spettacolo");
	            }
	        }
	    }
	    
	    Biglietto bigliettoAggiunto = bigliettoRepository.save(nuovoBiglietto);
	    
	    if (bigliettoAggiunto.getId() != null) {
	        return ResponseEntity.ok().body("Biglietto aggiunto");
	    } else {
	        return ResponseEntity.badRequest().body("Errore");
	    }
	}
	
	@DeleteMapping("/admin/diBiglietto/{id}")
	public ResponseEntity<String> rimuoviBiglietto(@PathVariable Long id) {
	    Optional<Biglietto> daRimuovere = bigliettoRepository.findById(id);
	    
	    if (daRimuovere.isPresent()) {
	        bigliettoRepository.delete(daRimuovere.get());
	        return ResponseEntity.ok().body("Biglietto rimosso");
	    } else {
	        return ResponseEntity.badRequest().body("Biglietto non trovato");
	    }
	}
}
