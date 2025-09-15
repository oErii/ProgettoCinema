package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.AttoreCreateRequestDTO;
import org.elis.demo.DTO.request.AttoreUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.model.Attore;
import org.springframework.stereotype.Component;

@Component
public class AttoreMapper {
	public static Attore toEntity(AttoreCreateRequestDTO dto) {
        Attore a = new Attore();
        a.setNome(dto.getNome());
        a.setCognome(dto.getCognome());
        return a;
    }

    public static void applyUpdates(Attore attore, AttoreUpdateRequestDTO dto) {
        if (dto.getNome() != null) {
            attore.setNome(dto.getNome());
        }
        if (dto.getCognome() != null) {
            attore.setCognome(dto.getCognome());
        }
    }

    public static AttoreResponseDTO toResponse(Attore a) {
        AttoreResponseDTO dto = new AttoreResponseDTO();
        dto.setId(a.getId());
        dto.setNome(a.getNome());
        dto.setCognome(a.getCognome());
        return dto;
    }
}
