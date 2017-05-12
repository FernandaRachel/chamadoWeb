package br.usjt.chamado.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.chamado.dao.ChamadoDAO;
import br.usjt.chamado.model.Chamado;
import br.usjt.chamado.model.Status;
import br.usjt.chamado.model.Usuario;

@Service
public class ChamadoService {

	private ChamadoDAO daoChamado;
	private SLAService serviceSLA;
	
	@Autowired
	public ChamadoService(ChamadoDAO daoChamado, SLAService serviceSLA) {
		this.daoChamado = daoChamado;
		this.serviceSLA = serviceSLA;
	}
	
	public void adicionar(Chamado chamado) {
		Calendar limite = Calendar.getInstance();
		limite.add(Calendar.HOUR,chamado.getSla().getSLATempo());
		chamado.setDtCadastro(new Date());
		chamado.setDtLimite(limite.getTime());
		chamado.setStatus(Status.ABERTO);
		if (chamado.getAtivo() == null)
			chamado.setAtivo(1);
		daoChamado.adicionar(chamado);
	}
	
	public void alterar(Chamado chamado) {
		Calendar limite = Calendar.getInstance();
		limite.add(Calendar.HOUR,chamado.getSla().getSLATempo());
		
		Chamado chamadolOld = buscaPorId(chamado.getId());
		chamado.setDtCadastro(chamadolOld.getDtCadastro());
		chamado.setDtLimite(chamadolOld.getDtLimite());
		chamado.setDtFimAtendimento(chamadolOld.getDtFimAtendimento());
		chamado.setDtInicioAtendimento(chamadolOld.getDtInicioAtendimento());
		chamado.setStatus(chamadolOld.getStatus());
		chamado.setSolicitante(chamadolOld.getSolicitante());
		chamado.setDtAlteracao(chamadolOld.getDtAlteracao());
		
		if (chamado.getAtivo() == null) {
			chamado.setAtivo(chamadolOld.getAtivo());
		}
		
		if (chamado.getSla().getId() != chamadolOld.getSla().getId()) {
			chamado.setDtLimite(limite.getTime());
			chamado.setDtAlteracao(new Date());
		}

		daoChamado.alterar(chamado);
	}
	
	public void remover(Chamado chamado) {
		daoChamado.remover(chamado);
	}
	
	public Chamado buscaPorId(Long id) {
		Chamado chamado = daoChamado.buscaPorId(id);
		String prazo = serviceSLA.calculaPrazo(chamado.getDtLimite());
		chamado.setPrazo(prazo);
		return chamado;
	}
	

	public List<Chamado> listarSolucionador(Usuario solicitante) {
        
		List<Chamado> lista = daoChamado.listarSolucionador(solicitante);
		for (Chamado chamado : lista) {
			String prazo = serviceSLA.calculaPrazo(chamado.getDtLimite());
			if (serviceSLA.estaAtrasado(chamado.getDtLimite()) && chamado.getStatus().equals(Status.ABERTO)) {
			  chamado.setStatus(Status.ATRASADO);
			}
			chamado.setPrazo(prazo);
		}
		return lista;
	}

	public void atender(Chamado chamado) {
		
		Chamado chamadoOld = buscaPorId(chamado.getId());
		chamado.setFila(chamadoOld.getFila());
		chamado.setSolicitante(chamadoOld.getSolicitante());
		chamado.setDtCadastro(chamadoOld.getDtCadastro());
		chamado.setDtAlteracao(chamadoOld.getDtAlteracao());	
		chamado.setDtLimite(chamadoOld.getDtLimite());
		chamado.setDtInicioAtendimento(chamadoOld.getDtInicioAtendimento());
		chamado.setDtFimAtendimento(chamadoOld.getDtFimAtendimento());
		chamado.setStatus(chamadoOld.getStatus());
		chamado.setAssunto(chamadoOld.getAssunto());
		chamado.setDescricao(chamadoOld.getDescricao());
		chamado.setSla(chamadoOld.getSla());
		
		if (chamado.getAtivo() == null) {
			chamado.setAtivo(chamadoOld.getAtivo());
		}	

		if (!chamado.getSolucao().equals("")) {
			if (chamado.getDtInicioAtendimento() == null) {
				chamado.setDtInicioAtendimento(new Date());
			}
			chamado.setStatus(Status.EMATENDIMENTO);
			System.out.println("finaliza"+chamado.getFinaliza());
			if (chamado.getFinaliza().equals("Sim")) {
				chamado.setStatus(Status.FECHADO);
				chamado.setDtFimAtendimento(new Date());
			}
			daoChamado.alterar(chamado);
		}
	}
}