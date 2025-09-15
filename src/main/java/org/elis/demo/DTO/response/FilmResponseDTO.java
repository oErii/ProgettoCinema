package org.elis.demo.DTO.response;

import java.util.List;

import lombok.Data;

@Data
public class FilmResponseDTO {
	private Long id;
    private String titolo;
    private Long durata;
    private Long annoUscita;
    private String genereNome;
    private List<AttoreResponseDTO> attori;
}
