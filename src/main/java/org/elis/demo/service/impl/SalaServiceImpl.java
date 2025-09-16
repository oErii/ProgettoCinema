package org.elis.demo.service.impl;

import java.util.Optional;

import org.elis.demo.DTO.mapper.SalaMapper;
import org.elis.demo.DTO.request.SalaCreateRequestDTO;
import org.elis.demo.DTO.request.SalaUpdateRequestDTO;
import org.elis.demo.DTO.response.SalaResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Sala;
import org.elis.demo.repository.SalaRepositoryJPA;
import org.elis.demo.service.definition.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SalaServiceImpl implements SalaService{

	@Autowired
	private SalaRepositoryJPA salaRepository;
	
	@Autowired
	private SalaMapper sMapper;
	
	@Override
    @Transactional
    public SalaResponseDTO aggiungi(SalaCreateRequestDTO request) throws ConflictException {
        String nome = request.getNome();
        if (salaRepository.findByNome(nome).isPresent()) {
            throw new ConflictException("Sala già esistente");
        }

        Sala entity = SalaMapper.toSala(request);
        entity.setNome(nome); // forza normalizzazione
        Sala saved = salaRepository.save(entity);

        return sMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SalaResponseDTO modifica(Long id, SalaUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Sala non trovata"));

        // se cambia nome, controlla conflitto
        if (request.getNome() != null) {
            String nuovoNome = (request.getNome());
            Optional<Sala> same = salaRepository.findByNome(nuovoNome);
            if (same.isPresent() && !same.get().getId().equals(sala.getId())) {
                throw new ConflictException("Sala già esistente");
            }
        }

        sMapper.applyUpdates(sala, request);
        Sala saved = salaRepository.save(sala);
        return sMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException, ConflictException {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Sala non trovata"));

        if (sala.getSpettacolo() != null && !sala.getSpettacolo().isEmpty()) {
            throw new ConflictException("Impossibile eliminare: sala associata a uno o più spettacoli");
        }
        
        salaRepository.delete(sala);
    }
	
}
