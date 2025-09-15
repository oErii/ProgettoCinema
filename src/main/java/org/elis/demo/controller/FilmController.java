package org.elis.demo.controller;

import org.elis.demo.DTO.request.FilmCreateRequestDTO;
import org.elis.demo.DTO.request.FilmUpdateRequestDTO;
import org.elis.demo.DTO.response.FilmResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.FilmService;
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
@RequestMapping("/cinema/film")
public class FilmController {

	@Autowired
	private FilmService filmS;
	
	@PostMapping
    public ResponseEntity<FilmResponseDTO> create(@Valid @RequestBody FilmCreateRequestDTO request) throws ConflictException, NessunRisultatoException {
        FilmResponseDTO dto = filmS.aggiungi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody FilmUpdateRequestDTO request) throws NessunRisultatoException, ConflictException {
        FilmResponseDTO dto = filmS.modifica(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NessunRisultatoException {
        filmS.rimuovi(id);
        return ResponseEntity.noContent().build();
    }
	
}