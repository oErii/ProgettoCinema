package org.elis.demo.DTO.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UtenteUpdateRequestDTO {
	private String nome;
    private String cognome;
    @Email
    private String email;
    private String password;
}
