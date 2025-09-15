package org.elis.demo.DTO.response;

import lombok.Data;

@Data
public class BigliettoResponseDTO {
	private Long id;
    private String posto;
    private Long spettacoloId;
    private String filmTitolo;
    private Long utenteId;
    private String utenteEmail;
}
