package org.elis.demo.DTO.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpettacoloResponseDTO {
	private Long id;
    private String filmTitolo;
    private Long filmId;
    private String salaNome;
    private Long salaId;
    private LocalDateTime dataOra;
    private Double prezzo;
}
