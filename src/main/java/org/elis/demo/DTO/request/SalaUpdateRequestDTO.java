package org.elis.demo.DTO.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SalaUpdateRequestDTO {
	private String nome;          // opzionale
    @Positive
    private Long numeroPosti;     // opzionale
}
