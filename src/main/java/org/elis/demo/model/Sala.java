package org.elis.demo.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sala {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Column(nullable = false)
	private Long numeroPosti;
	
	@OneToMany(mappedBy = "sala")
	List<Spettacolo> spettacolo;

	public Sala(Long id, String nome, Long numeroPosti) {
		super();
		this.id = id;
		this.nome = nome;
		this.numeroPosti = numeroPosti;
	}
	
	public Sala() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(Long numeroPosto) {
		this.numeroPosti = numeroPosto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, numeroPosti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		return id == other.id && Objects.equals(nome, other.nome) && numeroPosti == other.numeroPosti;
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", nome=" + nome + ", numeroPosti=" + numeroPosti + "]";
	}
	
	
}
