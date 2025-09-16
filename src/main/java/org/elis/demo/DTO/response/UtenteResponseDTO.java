package org.elis.demo.DTO.response;

import org.elis.demo.model.Ruolo;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@RequiredArgsConstructor
@Setter
public class UtenteResponseDTO {
	
	private Long id;
    private String email;
    private String nome;
    private String cognome;
    private Ruolo ruolo;
	public UtenteResponseDTO(Long id, String email, String nome, String cognome, Ruolo ruolo) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.ruolo = ruolo;
	}
 
    
}