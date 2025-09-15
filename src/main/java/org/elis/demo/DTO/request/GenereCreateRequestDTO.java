package org.elis.demo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenereCreateRequestDTO {
	@NotBlank
    private String nome;
}
