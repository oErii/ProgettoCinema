package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.FilmCreateRequestDTO;
import org.elis.demo.DTO.request.FilmUpdateRequestDTO;
import org.elis.demo.DTO.response.FilmResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface FilmService {

	FilmResponseDTO aggiungi(FilmCreateRequestDTO request) throws ConflictException, NessunRisultatoException;
    FilmResponseDTO modifica(Long id, FilmUpdateRequestDTO request) throws NessunRisultatoException, ConflictException;
    void rimuovi(Long id) throws NessunRisultatoException, ConflictException;
    
}
