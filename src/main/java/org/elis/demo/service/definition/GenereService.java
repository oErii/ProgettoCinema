package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.GenereCreateRequestDTO;
import org.elis.demo.DTO.request.GenereUpdateRequestDTO;
import org.elis.demo.DTO.response.GenereResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface GenereService{

	GenereResponseDTO aggiungi(GenereCreateRequestDTO request) throws ConflictException;
    GenereResponseDTO modifica(Long id, GenereUpdateRequestDTO request) throws ConflictException, NessunRisultatoException;
    void rimuovi(Long id) throws NessunRisultatoException;
	
}
