package org.elis.demo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttoreCreateRequestDTO {
	@NotBlank
    private String nome;
    @NotBlank
    private String cognome;
}
