package org.elis.demo.controller;

import org.elis.demo.DTO.request.BigliettoCreateRequestDTO;
import org.elis.demo.DTO.request.BigliettoUpdateRequestDTO;
import org.elis.demo.DTO.response.BigliettoResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

import org.elis.demo.service.definition.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class BigliettoController {

    @Autowired
    private BigliettoService bigliettoService;

    @PostMapping("/admin/cBiglietto")
    public ResponseEntity<BigliettoResponseDTO> create(@Valid @RequestBody BigliettoCreateRequestDTO request)
            throws NessunRisultatoException, ConflictException {
        BigliettoResponseDTO dto = bigliettoService.aggiungi(request);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/admin/uBiglietto/{id}")
    public ResponseEntity<BigliettoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BigliettoUpdateRequestDTO request)
            throws NessunRisultatoException, ConflictException {
        return ResponseEntity.ok(bigliettoService.modifica(id, request));
    }

    @DeleteMapping("/admin/dBiglietto/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
            throws NessunRisultatoException {
        bigliettoService.rimuovi(id);
        return ResponseEntity.noContent().build();
    }
}