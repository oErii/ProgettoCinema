package org.elis.demo.service.impl;

import org.elis.demo.DTO.mapper.UtenteMapper;
import org.elis.demo.DTO.request.LoginRequestDTO;
import org.elis.demo.DTO.request.RegistrazioneRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Utente;
import org.elis.demo.repository.UtenteRepositoryJPA;
import org.elis.demo.service.definition.AutenticazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class AutenticazioneServiceImpl implements AutenticazioneService{

	@Autowired
    private final UtenteRepositoryJPA utenteRepository;
	@Autowired
	private final UtenteMapper uMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UtenteResponseDTO registrazione(RegistrazioneRequestDTO request) throws ConflictException {
		if (utenteRepository.findByEmail(request.getEmail()).isPresent())
			throw new ConflictException("Email già registrata");
		Utente utente = uMapper.toUtente(request);
		utente.setRuolo(request.getRuolo());
		utente.setPassword(passwordEncoder.encode(request.getPassword()));
		Utente salvato = utenteRepository.save(utente);
		return uMapper.toUtenteDTO(salvato);
	}
	
	@Override
	public UtenteResponseDTO login(LoginRequestDTO request) throws NessunRisultatoException {
		Utente utente = utenteRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new NessunRisultatoException("Utente non trovato, riprova"));
	    if (!passwordEncoder.matches(request.getPassword(), utente.getPassword()))
	            throw new NessunRisultatoException("Credenziali non valide");
	    return uMapper.toUtenteDTO(utente);
	}
}