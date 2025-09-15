package org.elis.demo.service.impl;

import org.elis.demo.DTO.mapper.SpettacoloMapper;
import org.elis.demo.DTO.request.SpettacoloCreateRequestDTO;
import org.elis.demo.DTO.request.SpettacoloUpdateRequestDTO;
import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Film;
import org.elis.demo.model.Sala;
import org.elis.demo.model.Spettacolo;
import org.elis.demo.repository.SpettacoloRepositoryJPA;
import org.elis.demo.repository.FilmRepositoryJPA;
import org.elis.demo.repository.SalaRepositoryJPA;
import org.elis.demo.service.definition.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SpettacoloServiceImpl implements SpettacoloService {

    @Autowired
    private SpettacoloRepositoryJPA spettacoloRepository;

    @Autowired
    private FilmRepositoryJPA filmRepository;

    @Autowired
    private SalaRepositoryJPA salaRepository;
    
    @Autowired
    private SpettacoloMapper sMapper;

    @Override
    @Transactional
    public SpettacoloResponseDTO aggiungi(SpettacoloCreateRequestDTO request) throws ConflictException, NessunRisultatoException {
        // carica Film e Sala per NOME/TITOLO
        Film film = filmRepository.findByTitolo(request.getFilmTitolo())
                .orElseThrow(() -> new NessunRisultatoException("Film non trovato"));

        Sala sala = salaRepository.findByNome(request.getSalaNome())
                .orElseThrow(() -> new NessunRisultatoException("Sala non trovata"));

        // sala occupata allo stesso orario?
        if (spettacoloRepository.findBySala_IdAndDataOra(sala.getId(), request.getDataOra()).isPresent()) {
            throw new ConflictException("Sala occupata a quell'orario");
        }

        Spettacolo entity = sMapper.toSpettacolo(request, film, sala);
        Spettacolo saved = spettacoloRepository.save(entity);

        return sMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SpettacoloResponseDTO modifica(Long id, SpettacoloUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {
        Spettacolo s = spettacoloRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Spettacolo non trovato"));

        Film maybeFilm = null;
        if (request.getFilmTitolo() != null) {
            maybeFilm = filmRepository.findByTitolo(request.getFilmTitolo())
                    .orElseThrow(() -> new NessunRisultatoException("Film non trovato"));
        }

        Sala maybeSala = null;
        if (request.getSalaNome() != null) {
            maybeSala = salaRepository.findByNome(request.getSalaNome())
                    .orElseThrow(() -> new NessunRisultatoException("Sala non trovata"));
        }

        // Se cambiano sala e/o dataOra, controlla conflitto
        Long salaIdToCheck = (maybeSala != null) ? maybeSala.getId() : s.getSala().getId();
        java.time.LocalDateTime dataOraToCheck = (request.getDataOra() != null) ? request.getDataOra() : s.getOrario();

        java.util.Optional<Spettacolo> sameSlot = spettacoloRepository.findBySala_IdAndDataOra(salaIdToCheck, dataOraToCheck);
        if (sameSlot.isPresent() && !sameSlot.get().getId().equals(s.getId())) {
            throw new ConflictException("Sala occupata a quell'orario");
        }

        sMapper.applyUpdates(s, request, maybeFilm, maybeSala);
        Spettacolo saved = spettacoloRepository.save(s);
        return sMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException {
        Spettacolo s = spettacoloRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Spettacolo non trovato"));
        spettacoloRepository.delete(s);
    }
}
