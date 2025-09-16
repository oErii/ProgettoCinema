package org.elis.demo.DTO.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SalaUpdateRequestDTO {
	private String nome;
    @Positive
    private Long numeroPosti;
}
