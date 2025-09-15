package org.elis.demo.DTO.request;

import java.util.List;

import lombok.Data;

@Data
public class FilmUpdateRequestDTO {
	private String titolo;
    private Long durata;
    private Long annoUscita;
    private String genereNome;
    private List<AttoreRefDTO> attori;
}
