package org.elis.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.demo.DTO.mapper.FilmMapper;
import org.elis.demo.DTO.request.FilmCreateRequestDTO;
import org.elis.demo.DTO.request.FilmUpdateRequestDTO;
import org.elis.demo.DTO.response.FilmResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Attore;
import org.elis.demo.model.Film;
import org.elis.demo.model.Genere;
import org.elis.demo.repository.AttoreRepositoryJPA;
import org.elis.demo.repository.FilmRepositoryJPA;
import org.elis.demo.repository.GenereRepositoryJPA;
import org.elis.demo.service.definition.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FilmServiceImpl implements FilmService{

	@Autowired
	private FilmRepositoryJPA filmRepository;
	
	@Autowired
	private GenereRepositoryJPA genereRepository;
	
	@Autowired
	private AttoreRepositoryJPA attoreRepository;
	
	@Override
    @Transactional
    public FilmResponseDTO aggiungi(FilmCreateRequestDTO request) throws ConflictException, NessunRisultatoException {
        String titolo = (request.getTitolo());
        if (filmRepository.findByTitolo(titolo).isPresent()) {
            throw new ConflictException("Titolo già esistente");
        }

        // Genere: usa se esiste, altrimenti crealo (stile lezioni)
        Genere genere = genereRepository.findById(request.getGenereId())
                .orElseThrow(() -> new NessunRisultatoException("Genere non trovato"));
        
        
        // Attori: per ognuno usa se esiste, altrimenti crea
        List<Attore> attori = new ArrayList<Attore>();
        List<Attore> fromDto = FilmMapper.toAttoriEntity(request.getAttori());
        for (Attore a : fromDto) {
            Optional<Attore> existing = attoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome());
            if (existing.isPresent()) attori.add(existing.get());
            else attori.add(attoreRepository.save(a));
        }

        Film entity = FilmMapper.toEntity(request, genere, attori);
        entity.setTitolo(titolo);

        Film saved = filmRepository.save(entity);
        return FilmMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public FilmResponseDTO modifica(Long id, FilmUpdateRequestDTO request) throws NessunRisultatoException, ConflictException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Film non trovato"));

        // titolo in conflitto?
        if (request.getTitolo() != null) {
            String nuovo = (request.getTitolo());
            Optional<Film> same = filmRepository.findByTitolo(nuovo);
            if (same.isPresent() && !same.get().getId().equals(film.getId())) {
                throw new ConflictException("Titolo già esistente");
            }
        }

        // Genere opzionale
        Genere maybeGenere = null;
        if (request.getGenereNome() != null) {
            Optional<Genere> og = genereRepository.findByNome(request.getGenereNome());
            if (og.isPresent()) maybeGenere = og.get();
            else {
                Genere g = new Genere();
                g.setNome(request.getGenereNome());
                maybeGenere = genereRepository.save(g);
            }
        }

        // Attori opzionali (sostituzione lista completa)
        List<Attore> maybeAttori = null;
        if (request.getAttori() != null) {
            List<Attore> attori = new ArrayList<Attore>();
            List<Attore> fromDto = FilmMapper.toAttoriEntity(request.getAttori());
            for (Attore a : fromDto) {
                Optional<Attore> existing = attoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome());
                if (existing.isPresent()) attori.add(existing.get());
                else attori.add(attoreRepository.save(a));
            }
            maybeAttori = attori;
        }

        FilmMapper.applyUpdates(film, request, maybeGenere, maybeAttori);
        Film saved = filmRepository.save(film);
        return FilmMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) throws NessunRisultatoException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NessunRisultatoException("Film non trovato"));
        filmRepository.delete(film);
    }
	
}
