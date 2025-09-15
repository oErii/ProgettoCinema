package org.elis.demo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SalaCreateRequestDTO {
	@NotBlank
    private String nome;

    @NotNull
    @Positive
    private Long numeroPosti;
}
