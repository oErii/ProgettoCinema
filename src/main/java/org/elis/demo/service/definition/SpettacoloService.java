package org.elis.demo.service.definition;


import java.time.LocalDate;
import java.util.List;

import org.elis.demo.DTO.request.SpettacoloCreateRequestDTO;
import org.elis.demo.DTO.request.SpettacoloUpdateRequestDTO;
import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Spettacolo;

public interface SpettacoloService {

	SpettacoloResponseDTO aggiungi(SpettacoloCreateRequestDTO request) throws ConflictException, NessunRisultatoException;
    SpettacoloResponseDTO modifica(Long id, SpettacoloUpdateRequestDTO request) throws ConflictException, NessunRisultatoException;
    void rimuovi(Long id) throws NessunRisultatoException;
	List<Spettacolo> programmazioneFilm(Long filmID) throws NessunRisultatoException;
	List<Spettacolo> programmazioneData(LocalDate data) throws NessunRisultatoException, ConflictException;
   
}
