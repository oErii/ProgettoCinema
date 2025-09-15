package org.elis.demo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BigliettoCreateRequestDTO {
	@NotBlank
    private String posto;

    @NotNull
    private Long spettacoloId;

    @NotNull
    private Long utenteId;
}
