package org.elis.demo.controller;

import org.elis.demo.DTO.request.LoginRequestDTO;
import org.elis.demo.DTO.request.RegistrazioneRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.service.definition.AutenticazioneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/all")
@Data
@RequiredArgsConstructor
public class AutenticazioneController {

	private final AutenticazioneService aService;
	
	@PostMapping("/registrazione")
	public ResponseEntity<UtenteResponseDTO> registrazione(@Valid @RequestBody RegistrazioneRequestDTO request) throws ConflictException {
		UtenteResponseDTO resp = aService.registrazione(request);
        return ResponseEntity.ok().body(resp);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UtenteResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) throws NessunRisultatoException {
		UtenteResponseDTO resp = aService.login(request);
	    return ResponseEntity.ok().body(resp);
	    
	}
}
