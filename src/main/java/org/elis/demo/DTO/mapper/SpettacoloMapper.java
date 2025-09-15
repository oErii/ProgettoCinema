package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.SpettacoloCreateRequestDTO;
import org.elis.demo.DTO.request.SpettacoloUpdateRequestDTO;
import org.elis.demo.DTO.response.SpettacoloResponseDTO;
import org.elis.demo.model.Film;
import org.elis.demo.model.Sala;
import org.elis.demo.model.Spettacolo;

public class SpettacoloMapper {
	public Spettacolo toSpettacolo(SpettacoloCreateRequestDTO dto, Film film, Sala sala) {
        Spettacolo s = new Spettacolo();
        s.setFilm(film);
        s.setSala(sala);
        s.setOrario(dto.getDataOra());
        s.setPrezzo(dto.getPrezzo());
        return s;
    }

    public void applyUpdates(Spettacolo s, SpettacoloUpdateRequestDTO dto, Film maybeFilm, Sala maybeSala) {
        if (maybeFilm != null) s.setFilm(maybeFilm);
        if (maybeSala != null) s.setSala(maybeSala);
        if (dto.getDataOra() != null) s.setOrario(dto.getDataOra());
        if (dto.getPrezzo() != null) s.setPrezzo(dto.getPrezzo());
    }

    public SpettacoloResponseDTO toResponse(Spettacolo s) {
        SpettacoloResponseDTO dto = new SpettacoloResponseDTO();
        dto.setId(s.getId());
        if (s.getFilm() != null) {
            dto.setFilmId(s.getFilm().getId());
            dto.setFilmTitolo(s.getFilm().getTitolo());
        }
        if (s.getSala() != null) {
            dto.setSalaId(s.getSala().getId());
            dto.setSalaNome(s.getSala().getNome());
        }
        dto.setDataOra(s.getOrario());
        dto.setPrezzo(s.getPrezzo());
        return dto;
    }
}
