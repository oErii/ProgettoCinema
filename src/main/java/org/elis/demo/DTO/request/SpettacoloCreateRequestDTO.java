package org.elis.demo.DTO.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SpettacoloCreateRequestDTO {
	@NotBlank
    private String filmTitolo;

    @NotBlank
    private String salaNome;

    @NotNull
    private LocalDateTime dataOra;

    @NotNull
    @Positive
    private Double prezzo;
}
