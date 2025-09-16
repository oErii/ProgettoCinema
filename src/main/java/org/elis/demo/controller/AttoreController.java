package org.elis.demo.controller;

import org.elis.demo.DTO.request.AttoreCreateRequestDTO;
import org.elis.demo.DTO.request.AttoreUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.AttoreService;
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
public class AttoreController {

	@Autowired
    private AttoreService attoreService;
	
	@PostMapping("/admin/cAttore")
    public ResponseEntity<AttoreResponseDTO> create(@Valid @RequestBody AttoreCreateRequestDTO request) throws ConflictException {
        AttoreResponseDTO dto = attoreService.aggiungi(request);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/admin/uAttore/{id}")
    public ResponseEntity<AttoreResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody AttoreUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        AttoreResponseDTO dto = attoreService.modifica(id, request);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/admin/dAttore/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException {
        attoreService.rimuovi(id);
        return ResponseEntity.ok().build();
    }
}

