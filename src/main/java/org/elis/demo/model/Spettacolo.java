package org.elis.demo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Spettacolo {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private double prezzo;
	
	@Column(nullable = false)
	private LocalDateTime orario;

	@ManyToOne
	private Film film;
	
	@OneToMany(mappedBy = "spettacolo")
	@JsonIgnore
	List<Biglietto> biglietti;
	
	@ManyToOne
	private Sala sala;
	
	public Spettacolo(Long id, double prezzo, LocalDateTime orario) {
		super();
		this.id = id;
		this.prezzo = prezzo;
		this.orario = orario;
	}

	public Spettacolo() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public LocalDateTime getOrario() {
		return orario;
	}

	public void setOrario(LocalDateTime orario) {
		this.orario = orario;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public List<Biglietto> getBiglietti() {
		return biglietti;
	}

	public void setBiglietti(List<Biglietto> biglietti) {
		this.biglietti = biglietti;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, orario, prezzo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spettacolo other = (Spettacolo) obj;
		return id == other.id && Objects.equals(orario, other.orario)
				&& Double.doubleToLongBits(prezzo) == Double.doubleToLongBits(other.prezzo);
	}

	@Override
	public String toString() {
		return "Spettacolo [id=" + id + ", prezzo=" + prezzo + ", orario=" + orario + "]";
	}
	
}
