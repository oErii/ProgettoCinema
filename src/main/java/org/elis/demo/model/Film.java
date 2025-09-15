package org.elis.demo.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Film {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titolo;
	
	@Column(nullable = false)
	private Long durata;
	
	@Column(nullable = false)
	private Long annoUscita;
	
	@ManyToOne
	private Genere genere;

	@ManyToMany
	private List<Attore> attori;
	
	@OneToMany(mappedBy = "film")
	@JsonIgnore
	List<Spettacolo> spettacoli;
	
	public Film(Long id, String titolo, Long durata, Long annoUscita) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.durata = durata;
		this.annoUscita = annoUscita;
	}

	public Film() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Long getDurata() {
		return durata;
	}

	public void setDurata(Long durata) {
		this.durata = durata;
	}

	public Long getAnnoUscita() {
		return annoUscita;
	}

	public void setAnnoUscita(Long annoUscita) {
		this.annoUscita = annoUscita;
	}
	
	

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere genere) {
		this.genere = genere;
	}

	public List<Attore> getAttori() {
		return attori;
	}

	public void setAttori(List<Attore> attori) {
		this.attori = attori;
	}

	public List<Spettacolo> getSpettacoli() {
		return spettacoli;
	}

	public void setSpettacoli(List<Spettacolo> spettacoli) {
		this.spettacoli = spettacoli;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoUscita, durata, id, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return annoUscita == other.annoUscita && durata == other.durata && id == other.id
				&& Objects.equals(titolo, other.titolo);
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", titolo=" + titolo + ", durata=" + durata + ", annoUscita=" + annoUscita + "]";
	}
	
	
}
