package br.usjt.chamado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.chamado.dao.FilaDAO;
import br.usjt.chamado.model.Fila;

@Service
public class FilaService {

	private FilaDAO daoFila;

	@Autowired
	public FilaService(FilaDAO daoFila) {
		this.daoFila = daoFila;
	}
	
	public void adicionar(Fila fila) {
		daoFila.adicionar(fila);
	}
	
	public void alterar(Fila fila) {
		daoFila.alterar(fila);
	}
	
	public void remover(Fila fila) {
		daoFila.remover(fila);
	}
	
	public Fila buscaPorId(Long id) {
		return daoFila.buscaPorId(id);
	}
	
	public List<Fila> listar() {
		return daoFila.listar();
	}
	
	public Fila buscaPorNome(String nome) {
		return daoFila.buscaPorNome(nome);
	}
}