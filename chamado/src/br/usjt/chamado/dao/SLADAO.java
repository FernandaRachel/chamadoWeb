package br.usjt.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.chamado.model.SLA;

@Repository
public class SLADAO {

	@PersistenceContext
	EntityManager manager;

	public SLA buscaPorId(long i) {
		return manager.find(SLA.class, i);
	}

	@SuppressWarnings("unchecked")
	public List<SLA> listar() {
		return manager.createNamedQuery("SLA.listar").getResultList();
	}
	
	public SLA buscaPorDescricao(String descricao) {
		return (SLA) manager.createNamedQuery("SLA.buscaPorDescricao").
				setParameter("descricao", descricao).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<SLA> buscaPorUsuario(Long id) {
		return (List<SLA>) manager.createNamedQuery("SLA.buscaPorUsuario").
				setParameter("id", id).getResultList();
	}

}
