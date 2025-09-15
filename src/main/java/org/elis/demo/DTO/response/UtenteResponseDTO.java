package org.elis.demo.DTO.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UtenteResponseDTO {
	private Long id;
    private String email;
    private String nome;
    private String cognome;
    
	public UtenteResponseDTO(Long id, String email, String nome, String cognome) {
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
	}

    
}