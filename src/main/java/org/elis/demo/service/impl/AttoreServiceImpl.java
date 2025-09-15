package org.elis.demo.service.impl;


import java.util.Optional;

import org.elis.demo.DTO.mapper.AttoreMapper;
import org.elis.demo.DTO.request.AttoreCreateRequestDTO;
import org.elis.demo.DTO.request.AttoreUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Attore;
import org.elis.demo.repository.AttoreRepositoryJPA;
import org.elis.demo.service.definition.AttoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AttoreServiceImpl implements AttoreService{
	
	@Autowired
    private AttoreRepositoryJPA attoreRepository;

	@Override
    public AttoreResponseDTO aggiungi(AttoreCreateRequestDTO request) throws ConflictException {
        String nome = request.getNome();
        String cognome = request.getCognome();

        if (attoreRepository.findByNomeAndCognome(nome, cognome).isPresent()) {
            throw new ConflictException("Attore già esistente");
        }

        Attore entity = AttoreMapper.toEntity(request);
        entity.setNome(nome);
        entity.setCognome(cognome);

        Attore saved = attoreRepository.save(entity);
        return AttoreMapper.toResponse(saved);
    }

	@Override
	public AttoreResponseDTO modifica(Long id, AttoreUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
		Attore attore = attoreRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Attore non trovato"));

        String nuovoNome = request.getNome() != null ? request.getNome() : null;
        String nuovoCognome = request.getCognome() != null ? request.getCognome() : null;

        boolean cambianoEntrambi = nuovoNome != null && nuovoCognome != null;
        boolean cambiaSoloNome = nuovoNome != null && nuovoCognome == null;
        boolean cambiaSoloCognome = nuovoNome == null && nuovoCognome != null;

        if (cambianoEntrambi) {
            Optional<Attore> same = attoreRepository.findByNomeAndCognome(nuovoNome, nuovoCognome);
            if (same.isPresent() && !same.get().getId().equals(attore.getId())) {
                throw new ConflictException("Attore già esistente");
            }
        } else if (cambiaSoloNome) {
            String cognomeAttuale = attore.getCognome();
            Optional<Attore> same = attoreRepository.findByNomeAndCognome(nuovoNome, cognomeAttuale);
            if (same.isPresent() && !same.get().getId().equals(attore.getId())) {
                throw new ConflictException("Attore già esistente");
            }
        } else if (cambiaSoloCognome) {
            String nomeAttuale = attore.getNome();
            Optional<Attore> same = attoreRepository.findByNomeAndCognome(nomeAttuale, nuovoCognome);
            if (same.isPresent() && !same.get().getId().equals(attore.getId())) {
                throw new ConflictException("Attore già esistente");
            }
        }

        if (nuovoNome != null) attore.setNome(nuovoNome);
        if (nuovoCognome != null) attore.setCognome(nuovoCognome);

        Attore saved = attoreRepository.save(attore);
        return AttoreMapper.toResponse(saved);
	}

	@Override
	public void rimuovi(Long id) throws NessunRisultatoException {
		Attore attore = attoreRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Attore non trovato"));
        attoreRepository.delete(attore);
	}
	
	
	
}
