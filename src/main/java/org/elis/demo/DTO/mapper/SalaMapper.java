package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.SalaCreateRequestDTO;
import org.elis.demo.DTO.request.SalaUpdateRequestDTO;
import org.elis.demo.DTO.response.SalaResponseDTO;
import org.elis.demo.model.Sala;
import org.springframework.stereotype.Component;

@Component
public class SalaMapper {
	public static Sala toSala(SalaCreateRequestDTO dto) {
        Sala s = new Sala();
        s.setNome(dto.getNome());
        s.setNumeroPosti(dto.getNumeroPosti());
        return s;
    }

    public void applyUpdates(Sala sala, SalaUpdateRequestDTO dto) {
        if (dto.getNome() != null) {
            sala.setNome(dto.getNome());
        }
        if (dto.getNumeroPosti() != null) {
            sala.setNumeroPosti(dto.getNumeroPosti());
        }
    }

    public SalaResponseDTO toResponse(Sala s) {
        SalaResponseDTO dto = new SalaResponseDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setNumeroPosti(s.getNumeroPosti());
        return dto;
    }
}
