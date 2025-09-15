package org.elis.demo.DTO.request;

import org.elis.demo.model.Ruolo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RegistrazioneRequestDTO {
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@NotNull
	private Ruolo ruolo;
	
}
