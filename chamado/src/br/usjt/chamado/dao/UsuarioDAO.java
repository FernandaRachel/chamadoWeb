package br.usjt.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.chamado.model.Usuario;

@Repository
public class UsuarioDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Usuario usuario) {
		manager.persist(usuario);
	}

	public void alterar(Usuario usuario) {
		manager.merge(usuario);
	}

	public void remover(Usuario usuario) {
		Usuario remover = buscaPorId(usuario.getId());
		manager.remove(remover);
	}
	
	public Usuario buscaPorId(Long id) {
		return manager.find(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listar() {
		return manager.createNamedQuery("Usuario.listar").getResultList();
	}
	
	public Usuario buscaPorLogin(String login) {
		return (Usuario) manager.createNamedQuery("Usuario.buscaPorLogin").
				setParameter("login", login).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarSolucionador() {
		return manager.createNamedQuery("Usuario.listarSolucionador").getResultList();
	}

}
