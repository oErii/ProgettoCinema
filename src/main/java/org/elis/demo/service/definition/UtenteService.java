package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.UtenteCreateRequestDTO;
import org.elis.demo.DTO.request.UtenteUpdateRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface UtenteService {

	UtenteResponseDTO aggiungi(UtenteCreateRequestDTO request) throws ConflictException;
    UtenteResponseDTO modifica(Long id, UtenteUpdateRequestDTO request) throws NessunRisultatoException, ConflictException;
    void rimuovi(Long id) throws NessunRisultatoException, ConflictException;
	
}
