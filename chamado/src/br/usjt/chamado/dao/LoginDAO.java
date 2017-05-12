package br.usjt.chamado.dao;

import javax.persistence.EntityManager;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.usjt.chamado.model.Usuario;

@Transactional
@Repository
public class LoginDAO {

	@PersistenceContext
	EntityManager manager;

	public Usuario buscaUsuario(Usuario usuario) {
		try {
			return (Usuario) manager.createNamedQuery("Usuario.buscaUsuario").
					setParameter("login", usuario.getLogin()).
					setParameter("senha", usuario.getSenha()).getSingleResult();
		} catch (NoResultException nre) {
			return new Usuario();
		}
	}
}
