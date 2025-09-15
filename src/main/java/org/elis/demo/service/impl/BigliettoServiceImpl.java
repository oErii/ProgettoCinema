package org.elis.demo.service.impl;

import java.util.Optional;

import org.elis.demo.DTO.mapper.BigliettoMapper;
import org.elis.demo.DTO.request.BigliettoCreateRequestDTO;
import org.elis.demo.DTO.request.BigliettoUpdateRequestDTO;
import org.elis.demo.DTO.response.BigliettoResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Biglietto;
import org.elis.demo.model.Spettacolo;
import org.elis.demo.model.Utente;
import org.elis.demo.repository.BigliettoRepositoryJPA;
import org.elis.demo.repository.SpettacoloRepositoryJPA;
import org.elis.demo.repository.UtenteRepositoryJPA;
import org.elis.demo.service.definition.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BigliettoServiceImpl implements BigliettoService {

    @Autowired
    private BigliettoRepositoryJPA bigliettoRepository;

    @Autowired
    private SpettacoloRepositoryJPA spettacoloRepository;

    @Autowired
    private UtenteRepositoryJPA utenteRepository;

    @Override
    @Transactional
    public BigliettoResponseDTO aggiungi(BigliettoCreateRequestDTO request) throws NessunRisultatoException, ConflictException{
        Spettacolo spettacolo = spettacoloRepository.findById(request.getSpettacoloId())
                .orElseThrow(() -> new NessunRisultatoException("Spettacolo non trovato"));

        Utente utente = utenteRepository.findById(request.getUtenteId())
                .orElseThrow(() -> new NessunRisultatoException("Utente non trovato"));

        if (bigliettoRepository.findByPostoAndSpettacolo_Id(request.getPosto(), spettacolo.getId()).isPresent()) {
            throw new ConflictException("Posto già occupato");
        }

        Biglietto entity = BigliettoMapper.toEntity(request, spettacolo, utente);
        Biglietto saved = bigliettoRepository.save(entity);
        return BigliettoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public BigliettoResponseDTO modifica(Long id, BigliettoUpdateRequestDTO request) throws NessunRisultatoException, ConflictException{
        Biglietto b = bigliettoRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Biglietto non trovato"));

        // se cambia posto, verifica conflitto nello stesso spettacolo
        if (request.getPosto() != null) {
            Optional<Biglietto> same = bigliettoRepository.findByPostoAndSpettacolo_Id(request.getPosto(), b.getSpettacolo().getId());
            if (same.isPresent() && !same.get().getId().equals(b.getId())) {
                throw new ConflictException("Posto già occupato");
            }
        }

        Utente maybeUtente = null;
        if (request.getUtenteId() != null) {
            maybeUtente = utenteRepository.findById(request.getUtenteId())
                    .orElseThrow(() -> new NessunRisultatoException("Utente non trovato"));
        }

        BigliettoMapper.applyUpdates(b, request, maybeUtente);
        Biglietto saved = bigliettoRepository.save(b);
        return BigliettoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException {
        Biglietto b = bigliettoRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Biglietto non trovato"));
        bigliettoRepository.delete(b);
    }
}
