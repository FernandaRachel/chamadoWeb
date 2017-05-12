package br.usjt.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.chamado.model.Chamado;
import br.usjt.chamado.model.TipoUsuario;
import br.usjt.chamado.model.Usuario;

@Repository
public class ChamadoDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Chamado chamado) {
		manager.persist(chamado);
	}

	public void alterar(Chamado chamado) {
		manager.merge(chamado);
	}

	public void remover(Chamado chamado) {
		Chamado remover = buscaPorId(chamado.getId());
		manager.remove(remover);
	}
	
	public Chamado buscaPorId(Long id) {
		return manager.find(Chamado.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Chamado> listarSolucionador(Usuario usuario) {
		List<Chamado> lista = null;
		if(usuario.getTipoUsuario() == TipoUsuario.SOLICITANTE ) {
			lista = manager.createNamedQuery("Chamado.listarSolicitante")
					.setParameter("solicitante", usuario)
					.getResultList();
		} else if(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR )  {
			lista = manager.createNamedQuery("Chamado.listar").getResultList();			
		} else if(usuario.getTipoUsuario() == TipoUsuario.SOLUCIONADOR )  {
			lista = manager.createNamedQuery("Chamado.listarFila")
					.setParameter("fila", usuario.getFila())
					.getResultList();		
		}
		return lista;
	}

}
