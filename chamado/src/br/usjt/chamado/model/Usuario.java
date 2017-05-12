package br.usjt.chamado.model;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="usuario")
@NamedQueries({ 
	@NamedQuery(name = "Usuario.buscaUsuario", query = "select u from Usuario u where u.login=:login and u.senha=:senha"),
	@NamedQuery(name = "Usuario.listar", query = "select u from Usuario u order by u.nome"),
	@NamedQuery(name = "Usuario.buscaPorId", query = "select u from Usuario u where u.id=:id"), 
	@NamedQuery(name = "Usuario.buscaPorLogin", query = "select u from Usuario u where u.login=:login"),
	@NamedQuery(name = "Usuario.listarSolucionador", query = "select u from Usuario u where u.tipoUsuario = 'SOLUCIONADOR' order by u.nome"),
})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;	
	
	@NotEmpty(message = "Nome é obrigatório.")
	@Size(max = 100, message = "Tamanho máximo 100 caracteres.")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@NotEmpty(message = "Login é obrigatório.")
	@Size(max=15,message="Tamanho máximo 15 caracteres.")
	@Column(name="login",length=15,nullable=false)
	private String login;

	@NotNull
	@NotEmpty(message = "Senha é obrigatório.")
	@Size(min=6,message="Minimo 6 caracteres.")
	@Column(name="senha",length=6,nullable=false)
	private String senha;

	@NotNull
	@NotEmpty(message = "Celular é obrigatório.")
	@Column(name="celular",length = 15, nullable = false)
	private String celular;
    
	@CPF(message = "CPF é inválido")
	@NotEmpty(message = "O campo CPF é obrigatório.")
	@Column(name="cpf",length = 15, nullable = false)
	private String cpf;
	
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

	@NotNull
	@NotEmpty(message = "Email é obrigatório.")
	@Column(name="email",length = 50, nullable = false)
	private String email;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fila_id")
	@JsonManagedReference
	private Fila fila;	

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sla_id")
	@JsonManagedReference
	private SLA sla;

	@Column(name = "ativo", length = 1, nullable = false)    
	private Integer ativo;
	
	transient String confirmaSenha;
	
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	public String getConfirmaSenha() {
		return senha;
	}
	
	public void setFila(Fila fila) {
		this.fila = fila;
	}
	
	public Fila getFila() {
		return fila;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", celular=" + celular
				+ ", cpf=" + cpf + ", tipoUsuario=" + tipoUsuario + ", email=" + email + "]";
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
