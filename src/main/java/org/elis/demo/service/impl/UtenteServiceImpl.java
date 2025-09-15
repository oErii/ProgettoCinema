package org.elis.demo.service.impl;

import java.util.Optional;

import org.elis.demo.DTO.mapper.UtenteMapper;
import org.elis.demo.DTO.request.UtenteCreateRequestDTO;
import org.elis.demo.DTO.request.UtenteUpdateRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Utente;
import org.elis.demo.repository.UtenteRepositoryJPA;
import org.elis.demo.service.definition.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UtenteServiceImpl implements UtenteService{
	
	@Autowired
	private UtenteRepositoryJPA utenteRepository;
	
	@Autowired
	private UtenteMapper uMapper;
	
	@Override
    @Transactional
    public UtenteResponseDTO aggiungi(UtenteCreateRequestDTO request) throws ConflictException {
        String email = request.getEmail();
        if (utenteRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("Email già registrata");
        }

        Utente entity = UtenteMapper.toUtente(request);
        entity.setEmail(email); // forza normalizzazione
        Utente saved = utenteRepository.save(entity);

        return uMapper.toUtenteDTO(saved);
    }

    @Override
    @Transactional
    public UtenteResponseDTO modifica(Long id, UtenteUpdateRequestDTO request) throws NessunRisultatoException, ConflictException {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Utente non trovato"));

        // se cambia email, controlla conflitto
        if (request.getEmail() != null) {
            String nuovaEmail = request.getEmail();
            Optional<Utente> same = utenteRepository.findByEmail(nuovaEmail);
            if (same.isPresent() && !same.get().getId().equals(utente.getId())) {
                throw new ConflictException("Email già registrata");
            }
        }

        UtenteMapper.applyUpdates(utente, request);
        Utente saved = utenteRepository.save(utente);
        return uMapper.toUtenteDTO(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException, ConflictException {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Utente non trovato"));

        // Regola utile: non eliminare se ha biglietti
        if (utenteRepository.findById(id).isPresent()) {
            throw new ConflictException("Impossibile eliminare: utente con biglietti associati");
        }

        utenteRepository.delete(utente);
    }

}
