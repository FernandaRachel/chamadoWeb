package br.usjt.chamado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="fila")
@NamedQueries({ 
	@NamedQuery(name = "Fila.listar", query = "select f from Fila f order by f.descricao"),
	@NamedQuery(name = "Fila.buscaPorId", query = "select f from Fila f where f.id=:id"), 
	@NamedQuery(name = "Fila.buscaPorNome", query = "select f from Fila f where f.descricao=:descricao"),
})
public class Fila {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private Long id;
    
	@NotEmpty(message = "Descrição é obrigatório.")
	@Size(max = 100, message = "Tamanho máximo 100 caracteres.")
	@Column(name = "descricao", length = 100, nullable = false)    
	private String descricao;
    
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "gerente_id")
	@JsonManagedReference
	private Usuario gerente;

	@Column(name = "ativo", length = 1, nullable = false)    
	private Integer ativo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}
	
	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fila other = (Fila) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fila [id=" + id + ", descricao=" + descricao+"]";
	}
    
	
    
    
}
