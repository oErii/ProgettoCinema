package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.GenereCreateRequestDTO;
import org.elis.demo.DTO.response.GenereResponseDTO;
import org.elis.demo.model.Genere;

public class GenereMapper {
	public static Genere toEntity(GenereCreateRequestDTO dto) {
        Genere g = new Genere();
        g.setNome(dto.getNome());
        return g;
    }

    public static void applyUpdate(Genere genere, String nuovoNome) {
        if (nuovoNome != null) genere.setNome(nuovoNome);
    }

    public static GenereResponseDTO toResponse(Genere g) {
        GenereResponseDTO dto = new GenereResponseDTO();
        dto.setId(g.getId());
        dto.setNome(g.getNome());
        return dto;
    }
}
