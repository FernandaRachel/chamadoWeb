package br.usjt.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.chamado.model.Fila;

@Repository
public class FilaDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Fila fila) {
		manager.persist(fila);
	}

	public void alterar(Fila fila) {
		manager.merge(fila);
	}

	public void remover(Fila fila) {
		Fila remover = buscaPorId(fila.getId());
		manager.remove(remover);
	}
	
	public Fila buscaPorId(Long id) {
		return manager.find(Fila.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Fila> listar() {
		return manager.createNamedQuery("Fila.listar").getResultList();
	}
	
	public Fila buscaPorNome(String nome) {
		return (Fila) manager.createNamedQuery("Fila.buscaPorNome").
				setParameter("nome", nome).getSingleResult();
	}

}
