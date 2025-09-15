package org.elis.demo.DTO.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilmCreateRequestDTO {
	@NotBlank
    private String titolo;

    @NotNull
    private Long durata;

    @NotNull
    private Long  annoUscita;

    @NotNull
    private Long genereId;

    @NotNull
    private List<AttoreRefDTO> attori;
}
