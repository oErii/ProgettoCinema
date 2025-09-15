package org.elis.demo.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteCreateRequestDTO {
	@NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}
