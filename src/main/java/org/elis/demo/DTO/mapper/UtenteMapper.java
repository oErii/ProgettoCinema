package org.elis.demo.DTO.mapper;

import org.elis.demo.DTO.request.RegistrazioneRequestDTO;
import org.elis.demo.DTO.request.UtenteCreateRequestDTO;
import org.elis.demo.DTO.request.UtenteUpdateRequestDTO;
import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.model.Utente;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Component
public class UtenteMapper {
	
	public Utente toUtente(RegistrazioneRequestDTO request) {
		Utente u = new Utente();
		u.setCognome(request.getCognome());
		u.setPassword(request.getPassword());
		u.setNome(request.getNome());
		u.setEmail(request.getEmail());
		return u;
	}
	
	public static Utente toUtente(UtenteCreateRequestDTO dto) {
        Utente u = new Utente();
        u.setNome(dto.getNome());
        u.setCognome(dto.getCognome());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }
	
	public UtenteResponseDTO toUtenteDTO(Utente u) {
		return new UtenteResponseDTO(u.getId(), u.getEmail(), u.getNome(), u.getCognome());
	}

	public static void applyUpdates(Utente u, UtenteUpdateRequestDTO dto) {
        if (dto.getNome() != null)     u.setNome(dto.getNome());
        if (dto.getCognome() != null)  u.setCognome(dto.getCognome());
        if (dto.getEmail() != null)    u.setEmail(dto.getEmail());
        if (dto.getPassword() != null) u.setPassword(dto.getPassword());
    }
	
}
