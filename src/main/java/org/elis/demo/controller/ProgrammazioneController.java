package org.elis.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Spettacolo;
import org.elis.demo.service.definition.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all")
public class ProgrammazioneController {

	@Autowired
	private SpettacoloService sService;
	
	@GetMapping("/Programmazione/film/{filmID}")
	ResponseEntity<List<Spettacolo>> programmazioneXFilm(@PathVariable Long filmID) throws NessunRisultatoException{
		List<Spettacolo> listaFilm = sService.programmazioneFilm(filmID);
		return ResponseEntity.ok().body(listaFilm);
	}
	
	@GetMapping("/Programmazione/data/{data}")
	ResponseEntity<List<Spettacolo>> programmazioneXData(@PathVariable LocalDate data) throws NessunRisultatoException, ConflictException{
		List<Spettacolo> listaFilm = sService.programmazioneData(data);
		return ResponseEntity.ok().body(listaFilm);
	}
}
