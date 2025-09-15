package org.elis.demo.DTO.mapper;

import java.util.ArrayList;
import java.util.List;

import org.elis.demo.DTO.request.AttoreRefDTO;
import org.elis.demo.DTO.request.FilmCreateRequestDTO;
import org.elis.demo.DTO.request.FilmUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.DTO.response.FilmResponseDTO;
import org.elis.demo.model.Attore;
import org.elis.demo.model.Film;
import org.elis.demo.model.Genere;

public class FilmMapper {
	public static Film toEntity(FilmCreateRequestDTO dto, Genere genere, List<Attore> attori) {
        Film f = new Film();
        f.setTitolo(dto.getTitolo());
        f.setDurata(dto.getDurata());
        f.setAnnoUscita(dto.getAnnoUscita());
        f.setGenere(genere);
        f.setAttori(attori);
        return f;
    }

    public static void applyUpdates(Film film, FilmUpdateRequestDTO dto, Genere maybeGenere, List<Attore> maybeAttori) {
        if (dto.getTitolo() != null)     film.setTitolo(dto.getTitolo());
        if (dto.getDurata() != null)     film.setDurata(dto.getDurata());
        if (dto.getAnnoUscita() != null) film.setAnnoUscita(dto.getAnnoUscita());
        if (maybeGenere != null)         film.setGenere(maybeGenere);
        if (maybeAttori != null)         film.setAttori(maybeAttori);
    }

    public static FilmResponseDTO toResponse(Film f) {
        FilmResponseDTO dto = new FilmResponseDTO();
        dto.setId(f.getId());
        dto.setTitolo(f.getTitolo());
        dto.setDurata(f.getDurata());
        dto.setAnnoUscita(f.getAnnoUscita());
        if (f.getGenere() != null) dto.setGenereNome(f.getGenere().getNome());

        List<AttoreResponseDTO> out = new ArrayList<AttoreResponseDTO>();
        if (f.getAttori() != null) {
            for (Attore a : f.getAttori()) {
                AttoreResponseDTO ad = new AttoreResponseDTO();
                ad.setId(a.getId());
                ad.setNome(a.getNome());
                ad.setCognome(a.getCognome());
                out.add(ad);
            }
        }
        dto.setAttori(out);
        return dto;
    }

    public static List<Attore> toAttoriEntity(List<AttoreRefDTO> refs) {
        List<Attore> list = new ArrayList<Attore>();
        if (refs == null) return list;
        for (AttoreRefDTO r : refs) {
            Attore a = new Attore();
            a.setNome(r.getNome());
            a.setCognome(r.getCognome());
            list.add(a);
        }
        return list;
    }
}
