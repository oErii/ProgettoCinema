package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.SalaCreateRequestDTO;
import org.elis.demo.DTO.request.SalaUpdateRequestDTO;
import org.elis.demo.DTO.response.SalaResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface SalaService {

	SalaResponseDTO aggiungi(SalaCreateRequestDTO request) throws ConflictException;
    SalaResponseDTO modifica(Long id, SalaUpdateRequestDTO request) throws ConflictException, NessunRisultatoException;
    void rimuovi(Long id) throws NessunRisultatoException, ConflictException;	
}
