package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.BigliettoCreateRequestDTO;
import org.elis.demo.DTO.request.BigliettoUpdateRequestDTO;
import org.elis.demo.DTO.response.BigliettoResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface BigliettoService {

	BigliettoResponseDTO aggiungi(BigliettoCreateRequestDTO request) throws NessunRisultatoException, ConflictException;
    BigliettoResponseDTO modifica(Long id, BigliettoUpdateRequestDTO request) throws NessunRisultatoException, ConflictException;
    void rimuovi(Long id) throws NessunRisultatoException;	
}
