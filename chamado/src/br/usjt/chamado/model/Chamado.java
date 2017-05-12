package br.usjt.chamado.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="chamado")
@NamedQueries({ 
	@NamedQuery(name = "Chamado.buscaPorId", query = "select c from Chamado c where c.id=:id"), 
	@NamedQuery(name = "Chamado.listar", query = "select c from Chamado c order by c.dtCadastro"),
	@NamedQuery(name = "Chamado.listarSolicitante", query = "select c from Chamado c where c.solicitante = :solicitante and c.ativo=1 order by c.dtCadastro"),
	@NamedQuery(name = "Chamado.listarFila", query = "select c from Chamado c where c.fila = :fila and c.ativo=1 order by c.dtCadastro")
})
public class Chamado implements Serializable {

	private static final long serialVersionUID = 1758910374388042256L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fila_id")
	@JsonManagedReference	
	private Fila fila;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "solicitante_id")
	@JsonManagedReference	
	private Usuario solicitante;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dtcadastro", nullable = false)
	private Date dtCadastro;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dtalteracao", nullable = true)
	private Date dtAlteracao;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dtlimite", nullable = true)
	private Date dtLimite;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dtinicioatendimento", nullable = true)
	private Date dtInicioAtendimento;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dtfimatendimento", nullable = true)
	private Date dtFimAtendimento;
	
    @Enumerated(EnumType.STRING)
	private Status status;
    
	@NotEmpty
	@Column(name="assunto", nullable = false)
	@Size(max = 100, message = "Tamanho máximo 100 caracteres.")
	private String assunto;
	
	@NotEmpty
	@Column(name="descricao", nullable = false)
	@Size(max = 1000, message = "Tamanho máximo 1000 caracteres.")
	private String descricao;
	
	@Column(name="solucao", nullable = true)
	@Size(max = 1000, message = "Tamanho máximo 1000 caracteres.")
	private String solucao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sla_id")
	@JsonManagedReference
	private SLA sla;

	@Column(name = "ativo", length = 1, nullable = false)    
	private Integer ativo;
	
	transient String prazo;
	transient String finaliza;
	
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}
	public String getPrazo() {
		return prazo;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fila getFila() {
		return fila;
	}

	public void setFila(Fila fila) {
		this.fila = fila;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Date getDtLimite() {
		return dtLimite;
	}

	public void setDtLimite(Date dtLimite) {
		this.dtLimite = dtLimite;
	}

	public Date getDtInicioAtendimento() {
		return dtInicioAtendimento;
	}

	public void setDtInicioAtendimento(Date dtInicioAtendimento) {
		this.dtInicioAtendimento = dtInicioAtendimento;
	}

	public Date getDtFimAtendimento() {
		return dtFimAtendimento;
	}

	public void setDtFimAtendimento(Date dtFimAtendimento) {
		this.dtFimAtendimento = dtFimAtendimento;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public SLA getSla() {
		return sla;
	}

	public void setSla(SLA sla) {
		this.sla = sla;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}
	
	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
	public String getFinaliza() {
		return finaliza;
	}
	
	public void setFinaliza(String finaliza) {
		this.finaliza = finaliza;
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
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
