package org.elis.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.service.definition.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all/programmazione")
public class ProgrammazioneController {
	
	@Autowired
    private SpettacoloService spettacoloService;

    // per film
    @GetMapping("/{filmId}")
    public ResponseEntity<List<SpettacoloResponseDTO>> perFilm(@PathVariable Long filmId) {
        return ResponseEntity.ok(spettacoloService.listaPerFilm(filmId));
    }

    // per data (yyyy-MM-dd)
    @GetMapping("/{data}")
    public ResponseEntity<List<SpettacoloResponseDTO>> perData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(spettacoloService.listaPerData(data));
    }

    // combinato: /all/programmazione?filmId=..&data=yyyy-MM-dd
    @GetMapping
    public ResponseEntity<List<SpettacoloResponseDTO>> perFiltri(@RequestParam(required = false) Long filmId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(spettacoloService.listaPerFiltri(filmId, data));
    }
    
}
