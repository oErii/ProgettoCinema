package org.elis.demo.controller;

import org.elis.demo.DTO.request.GenereCreateRequestDTO;
import org.elis.demo.DTO.request.GenereUpdateRequestDTO;
import org.elis.demo.DTO.response.GenereResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.GenereService;
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
@RequestMapping("/genere")
public class GenereController {

	@Autowired
    private GenereService genereS;
	
	@PostMapping
    public ResponseEntity<GenereResponseDTO> create(@Valid @RequestBody GenereCreateRequestDTO request) throws ConflictException {
        GenereResponseDTO dto = genereS.aggiungi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenereResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody GenereUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        GenereResponseDTO dto = genereS.modifica(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException {
        genereS.rimuovi(id);
        return ResponseEntity.noContent().build();
    }
	 
}