package org.elis.demo.service.definition;

import java.time.LocalDate;
import java.util.List;

import org.elis.demo.DTO.request.SpettacoloCreateRequestDTO;
import org.elis.demo.DTO.request.SpettacoloUpdateRequestDTO;
import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface SpettacoloService {

	SpettacoloResponseDTO aggiungi(SpettacoloCreateRequestDTO request) throws ConflictException, NessunRisultatoException;
    SpettacoloResponseDTO modifica(Long id, SpettacoloUpdateRequestDTO request) throws ConflictException, NessunRisultatoException;
    void rimuovi(Long id) throws NessunRisultatoException;
    
    List<SpettacoloResponseDTO> listaPerFilm(Long filmId);
    List<SpettacoloResponseDTO> listaPerData(LocalDate data);
    List<SpettacoloResponseDTO> listaPerFiltri(Long filmId, LocalDate data);
}
