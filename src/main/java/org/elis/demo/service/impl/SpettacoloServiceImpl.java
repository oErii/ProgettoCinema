package org.elis.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        Film film = filmRepository.findByTitolo(request.getFilmTitolo())
                .orElseThrow(() -> new NessunRisultatoException("Film non trovato"));

        Sala sala = salaRepository.findByNome(request.getSalaNome())
                .orElseThrow(() -> new NessunRisultatoException("Sala non trovata"));

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

        Long salaIdToCheck = (maybeSala != null) ? maybeSala.getId() : s.getSala().getId();
        LocalDateTime dataOraToCheck = (request.getDataOra() != null) ? request.getDataOra() : s.getOrario();

        Optional<Spettacolo> sameSlot = spettacoloRepository.findBySala_IdAndDataOra(salaIdToCheck, dataOraToCheck);
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
    
    
    
    
    
    @Override
    public List<SpettacoloResponseDTO> listaPerFilm(Long filmId) {
        List<Spettacolo> entities = spettacoloRepository.findByFilm_IdOrderByDataOraAsc(filmId);
        List<SpettacoloResponseDTO> result = new ArrayList<>();
        for (Spettacolo s : entities) {
            result.add(sMapper.toResponse(s));
        }
        return result;
    }

    @Override
    public List<SpettacoloResponseDTO> listaPerData(LocalDate data) {
        LocalDateTime start = data.atStartOfDay();
        LocalDateTime end = data.atTime(LocalTime.MAX);
        List<Spettacolo> entities = spettacoloRepository.findByDataOraBetweenOrderByDataOraAsc(start, end);
        List<SpettacoloResponseDTO> result = new ArrayList<>();
        for (Spettacolo s : entities) {
            result.add(sMapper.toResponse(s));
        }
        return result;
    }

  
    @Override
    public List<SpettacoloResponseDTO> listaPerFiltri(Long filmId, LocalDate data) {
        List<Spettacolo> base;

        if (data != null) {
            LocalDateTime start = data.atStartOfDay();
            LocalDateTime end = data.atTime(LocalTime.MAX);
            base = spettacoloRepository.findByDataOraBetweenOrderByDataOraAsc(start, end);
        } else if (filmId != null) {
            base = spettacoloRepository.findByFilm_IdOrderByDataOraAsc(filmId);
        } else {
            base = spettacoloRepository.findAll();
        }

        List<Spettacolo> filtered = new ArrayList<>();
        

        for (Spettacolo s : base) {
            boolean ok = true;

            if (filmId != null) {
                if (s.getFilm() == null || s.getFilm().getId() == null || !s.getFilm().getId().equals(filmId)) ok = false;
            }

            if (ok) filtered.add(s);
            
        }

        Collections.sort(filtered, new Comparator<Spettacolo>() {
            @Override
            public int compare(Spettacolo a, Spettacolo b) {
                if (a.getOrario() == null && b.getOrario() == null) return 0;
                if (a.getOrario() == null) return 1;
                if (b.getOrario() == null) return -1;
                return a.getOrario().compareTo(b.getOrario());
            }
        });

        List<SpettacoloResponseDTO> result = new ArrayList<>();
        for (Spettacolo s : filtered) {
            result.add(sMapper.toResponse(s));
        }
        return result;
    }
    
}
