package org.elis.demo.controller;

import org.elis.demo.DTO.request.SpettacoloCreateRequestDTO;
import org.elis.demo.DTO.request.SpettacoloUpdateRequestDTO;
import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SpettacoloController {

	@Autowired
    private SpettacoloService spettacoloService;
	
	@PostMapping("/admin/cSpettacolo")
    public ResponseEntity<SpettacoloResponseDTO> create(@Valid @RequestBody SpettacoloCreateRequestDTO request) throws ConflictException, NessunRisultatoException {
        SpettacoloResponseDTO dto = spettacoloService.aggiungi(request);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/admin/uSpettacolo/{id}")
    public ResponseEntity<SpettacoloResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SpettacoloUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        SpettacoloResponseDTO dto = spettacoloService.modifica(id, request);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/admin/dSpettacolo/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException {
        spettacoloService.rimuovi(id);
        return ResponseEntity.ok().build();
    }
	
}
