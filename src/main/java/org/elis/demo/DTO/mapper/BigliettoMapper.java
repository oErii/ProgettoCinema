package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.BigliettoCreateRequestDTO;
import org.elis.demo.DTO.request.BigliettoUpdateRequestDTO;
import org.elis.demo.DTO.response.BigliettoResponseDTO;
import org.elis.demo.model.Biglietto;
import org.elis.demo.model.Spettacolo;
import org.elis.demo.model.Utente;
import org.springframework.stereotype.Component;

@Component
public class BigliettoMapper {
	public static Biglietto toEntity(BigliettoCreateRequestDTO dto, Spettacolo spettacolo, Utente utente) {
        Biglietto b = new Biglietto();
        b.setPosto(dto.getPosto());
        b.setSpettacolo(spettacolo);
        b.setUtente(utente);
        return b;
    }

    public static void applyUpdates(Biglietto b, BigliettoUpdateRequestDTO dto, Utente utente) {
        if (dto.getPosto() != null) b.setPosto(dto.getPosto());
        if (utente != null) b.setUtente(utente);
    }

    public static BigliettoResponseDTO toResponse(Biglietto b) {
        BigliettoResponseDTO dto = new BigliettoResponseDTO();
        dto.setId(b.getId());
        dto.setPosto(b.getPosto());
        if (b.getSpettacolo() != null) {
            dto.setSpettacoloId(b.getSpettacolo().getId());
            if (b.getSpettacolo().getFilm() != null) {
                dto.setFilmTitolo(b.getSpettacolo().getFilm().getTitolo());
            }
        }
        if (b.getUtente() != null) {
            dto.setUtenteId(b.getUtente().getId());
            dto.setUtenteEmail(b.getUtente().getEmail());
        }
        return dto;
    }

}
