package org.elis.demo.service.impl;

import java.util.Optional;

import org.elis.demo.DTO.mapper.GenereMapper;
import org.elis.demo.DTO.request.GenereCreateRequestDTO;
import org.elis.demo.DTO.request.GenereUpdateRequestDTO;
import org.elis.demo.DTO.response.GenereResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Genere;
import org.elis.demo.repository.GenereRepositoryJPA;
import org.elis.demo.service.definition.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class GenereServiceImpl implements GenereService{

	@Autowired
	private GenereRepositoryJPA genereRepository;
	
	@Override
    @Transactional
    public GenereResponseDTO aggiungi(GenereCreateRequestDTO request) throws ConflictException {
        String nome= request.getNome();
        if (genereRepository.findByNome(nome).isPresent()) {
            throw new ConflictException("Genere già esistente");
        }
        Genere entity = GenereMapper.toEntity(request);
        entity.setNome(nome);
        Genere saved = genereRepository.save(entity);
        return GenereMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public GenereResponseDTO modifica(Long id, GenereUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        Genere genere = genereRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Genere non trovato"));

        if (request.getNome() != null) {
            String nuovoNome = request.getNome();
            Optional<Genere> same = genereRepository.findByNome(nuovoNome);
            if (same.isPresent() && !same.get().getId().equals(genere.getId())) {
                throw new ConflictException("Genere già esistente");
            }
            GenereMapper.applyUpdate(genere, nuovoNome);
        }

        Genere saved = genereRepository.save(genere);
        return GenereMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException, ConflictException {
        Genere genere = genereRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Genere non trovato"));

        if (genere.getFilm() != null && !genere.getFilm().isEmpty()) {
            throw new ConflictException("Impossibile eliminare: genere associato a uno o più film");
        }
        
        genereRepository.delete(genere);
    }

	
}
