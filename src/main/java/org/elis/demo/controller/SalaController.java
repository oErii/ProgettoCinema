package org.elis.demo.controller;

import org.elis.demo.DTO.request.SalaCreateRequestDTO;
import org.elis.demo.DTO.request.SalaUpdateRequestDTO;
import org.elis.demo.DTO.response.SalaResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.SalaService;
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
@RequestMapping
public class SalaController {

	@Autowired
    private SalaService salaS;
	
	@PostMapping("/admin/cSala")
    public ResponseEntity<SalaResponseDTO> create(@Valid @RequestBody SalaCreateRequestDTO request) throws ConflictException {
        SalaResponseDTO dto = salaS.aggiungi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/admin/uSala/{id}")
    public ResponseEntity<SalaResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody SalaUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        SalaResponseDTO dto = salaS.modifica(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/admin/dSala/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException {
        salaS.rimuovi(id);
        return ResponseEntity.noContent().build();
    }
	 
}