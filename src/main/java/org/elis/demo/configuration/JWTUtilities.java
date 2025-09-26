package org.elis.demo.configuration;
import java.security.SignatureException;

import java.util.Date;

import javax.crypto.SecretKey;

import org.elis.demo.DTO.response.UtenteResponseDTO;
import org.elis.demo.model.Ruolo;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtilities {

	private static final String KEY = "ChiaveAlfaNumericaSegretataDiAlmeno256BitA0%#0!@LKMF)A)F(UIAS)F_JAKLSFJ)#R)(";
	
	private SecretKey getSignatureKey() {
		return Keys.hmacShaKeyFor(KEY.getBytes());
	}
	
	public String generaToken(UtenteResponseDTO utente) {
		long durataToken = 1000L * 60 * 60 * 24 * 30;
		String token = Jwts.builder()
						.subject(utente.getEmail())
						.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis() + durataToken))
						.claims()
						.add("ruolo", utente.getRuolo().name())
						.add("nome", utente.getNome())
						.and()
						.signWith(getSignatureKey())
						.compact();
		return token;
	}
	
	private Claims getPayload(String token) throws SignatureException {
		return Jwts.parser()
				.verifyWith(getSignatureKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public Date getIssuedAt(String token) throws SignatureException {
		return getPayload(token).getIssuedAt();
	}
	
	public Date getExpiration(String token) throws SignatureException {
		return getPayload(token).getExpiration();
	}
	
	public Ruolo getRuolo(String token) throws SignatureException {
		return getPayload(token).get("ruolo", Ruolo.class);
	}
	
	public String getNome(String token) throws SignatureException {
		return getPayload(token).get("nome", String.class);
	}
	
	public String getSubject(String token) throws SignatureException {
		return getPayload(token).getSubject();
	}
}
