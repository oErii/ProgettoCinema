package org.elis.demo.controller;

import org.elis.demo.DTO.request.UtenteCreateRequestDTO;
import org.elis.demo.DTO.request.UtenteUpdateRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
	
	@Autowired
    private UtenteService utenteService;
	
	 @PostMapping
	    public ResponseEntity<UtenteResponseDTO> create(@Valid @RequestBody UtenteCreateRequestDTO request) throws ConflictException {
	        UtenteResponseDTO dto = utenteService.aggiungi(request);
	        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<UtenteResponseDTO> update(@PathVariable Long id,
	                                                    @Valid @RequestBody UtenteUpdateRequestDTO request) throws NessunRisultatoException, ConflictException {
	        UtenteResponseDTO dto = utenteService.modifica(id, request);
	        return ResponseEntity.ok(dto);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException, ConflictException {
	        utenteService.rimuovi(id);
	        return ResponseEntity.noContent().build();
	    }
}
