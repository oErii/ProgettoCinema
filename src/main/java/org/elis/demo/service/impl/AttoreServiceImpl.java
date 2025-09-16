package org.elis.demo.service.impl;


import java.util.Optional;

import org.elis.demo.DTO.mapper.AttoreMapper;
import org.elis.demo.DTO.request.AttoreCreateRequestDTO;
import org.elis.demo.DTO.request.AttoreUpdateRequestDTO;
import org.elis.demo.DTO.response.AttoreResponseDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.elis.demo.model.Attore;
import org.elis.demo.repository.AttoreRepositoryJPA;
import org.elis.demo.service.definition.AttoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AttoreServiceImpl implements AttoreService{
	
	@Autowired
    private AttoreRepositoryJPA attoreRepository;

	@Override
    public AttoreResponseDTO aggiungi(AttoreCreateRequestDTO request) throws ConflictException {
        //Riceve DTO (nome, cognome)
		String nome = request.getNome();
        String cognome = request.getCognome();

        if (attoreRepository.findByNomeAndCognome(nome, cognome).isPresent()) {
            throw new ConflictException("Attore già esistente");  //Verifica se quell’attore esiste già nel database
        }
        
        Attore entity = AttoreMapper.toEntity(request);
        
        Attore saved = attoreRepository.save(entity);
        
        return AttoreMapper.toResponse(saved);
    }

	@Override
	public AttoreResponseDTO modifica(Long id, AttoreUpdateRequestDTO request) throws ConflictException, NessunRisultatoException {

	    // Cerco l'attore, se non esiste lancio eccezione
	    Attore attore = attoreRepository.findById(id)
	            .orElseThrow(() -> new NessunRisultatoException("Attore non trovato"));

	    // Valori finali (se non arrivano nel DTO, uso quelli già presenti)
	    String finalNome = request.getNome() != null ? request.getNome() : attore.getNome();
	    String finalCognome = request.getCognome() != null ? request.getCognome() : attore.getCognome();

	    // Controllo se cambia davvero qualcosa
	    boolean cambiaNome = !finalNome.equals(attore.getNome());
	    boolean cambiaCognome = !finalCognome.equals(attore.getCognome());

	    if (cambiaNome || cambiaCognome) {
	        // Controllo se esiste già un attore con la stessa coppia (nome, cognome)
	        Optional<Attore> same = attoreRepository.findByNomeAndCognome(finalNome, finalCognome);
	        if (same.isPresent() && !same.get().getId().equals(attore.getId())) {
	            throw new ConflictException("Attore già esistente");
	        }

	        // Applico le modifiche
	        attore.setNome(finalNome);
	        attore.setCognome(finalCognome);

	        attore = attoreRepository.save(attore);
	    }

	    return AttoreMapper.toResponse(attore);
	}

	@Override
	public void rimuovi(Long id) throws NessunRisultatoException {
		Attore attore = attoreRepository.findById(id).orElseThrow(() -> new NessunRisultatoException("Attore non trovato"));
        attoreRepository.delete(attore);
	}
	
	
	
}
