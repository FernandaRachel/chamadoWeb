package br.usjt.chamado.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sla")
@NamedQueries({ 
	@NamedQuery(name = "SLA.listar", query = "select s from SLA s order by s.prioridade"),
	@NamedQuery(name = "SLA.buscaPorId", query = "select s from SLA s where s.id=:id"), 
	@NamedQuery(name = "SLA.buscaPorDescricao", query = "select s from SLA s where s.descricao=:descricao"),
	@NamedQuery(name = "SLA.buscaPorUsuario", query = "select s from SLA s where s.id >= :id")
})
public class SLA implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private Long id;
    
	@NotEmpty(message = "Descrição é obrigatório.")
	@Size(max = 30, message = "Tamanho máximo 30 caracteres.")
	@Column(name = "descricao", length = 30, nullable = false)    
	private String descricao;
	
	@NotEmpty(message = "Tempo em horas para o atendimento é obrigatório.")
	@NotNull
	@Column(name = "slatempo", length = 1, nullable = false)    
	private Integer SLATempo;

	@NotNull
	@Column(name = "prioridade", length = 2, nullable = false)    
	private Integer prioridade;

	@Column(name = "ativo", length = 1, nullable = false)    
	private Integer ativo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getSLATempo() {
		return SLATempo;
	}

	public void setSLATempo(Integer sLATempo) {
		SLATempo = sLATempo;
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
		SLA other = (SLA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SLA [id=" + id + ", descricao=" + descricao + ", SLATempo=" + SLATempo + "]";
	}
	
}
