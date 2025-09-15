package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.AttoreCreateRequestDTO;
import org.elis.demo.DTO.request.AttoreUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;


public interface AttoreService {

	AttoreResponseDTO aggiungi(AttoreCreateRequestDTO request) throws ConflictException;
    AttoreResponseDTO modifica(Long id, AttoreUpdateRequestDTO request) throws ConflictException, NessunRisultatoException;
    void rimuovi(Long id) throws NessunRisultatoException;
    
}
