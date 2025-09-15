package org.elis.demo.service.definition;

import org.elis.demo.DTO.request.LoginRequestDTO;
import org.elis.demo.DTO.request.RegistrazioneRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;

public interface AutenticazioneService{

    UtenteResponseDTO registrazione(RegistrazioneRequestDTO request) throws ConflictException;
    UtenteResponseDTO login(LoginRequestDTO request) throws NessunRisultatoException;

}
