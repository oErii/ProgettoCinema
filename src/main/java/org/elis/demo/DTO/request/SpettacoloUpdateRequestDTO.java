package org.elis.demo.DTO.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SpettacoloUpdateRequestDTO {
	private String filmTitolo;
    private String salaNome;
    private LocalDateTime dataOra;
    @Positive
    private Double prezzo;
}
