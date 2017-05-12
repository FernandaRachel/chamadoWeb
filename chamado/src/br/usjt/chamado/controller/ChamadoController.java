package br.usjt.chamado.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.usjt.chamado.model.Chamado;
import br.usjt.chamado.model.Fila;
import br.usjt.chamado.model.SLA;
import br.usjt.chamado.model.Usuario;
import br.usjt.chamado.properties.FilaPropertyEditor;
import br.usjt.chamado.properties.SLAPropertyEditor;
import br.usjt.chamado.service.ChamadoService;
import br.usjt.chamado.service.FilaService;
import br.usjt.chamado.service.SLAService;

@Controller
@Transactional
@RequestMapping("/chamados")
public class ChamadoController {
	
	private ChamadoService serviceChamado;
	private FilaService serviceFila;
	private SLAService serviceSLA;
	private FilaPropertyEditor filaPropertyEditor;
	private SLAPropertyEditor slaPropertyEditor;
	
	@Autowired
	public ChamadoController(ChamadoService serviceChamado, FilaPropertyEditor filaPropertyEditor, FilaService serviceFila,SLAService serviceSLA, 
			SLAPropertyEditor slaPropertyEditor ) {
		this.serviceChamado = serviceChamado;
		this.filaPropertyEditor = filaPropertyEditor;
		this.serviceFila = serviceFila;
		this.serviceSLA = serviceSLA;
		this.slaPropertyEditor = slaPropertyEditor;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listaTodos(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("chamados",serviceChamado.listarSolucionador(usuario));
		model.addAttribute("login", usuario);
		return "chamado/lista";
	}
	
	@RequestMapping(value="/novo")
	public String addChamado(Model model, HttpSession session) {
		Long idSla = null;
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("chamado",new Chamado());
		model.addAttribute("filas",serviceFila.listar());
		if(usuario.getSla() == null)
			idSla = 1L;
		else {
			idSla = usuario.getSla().getId();
		}
		model.addAttribute("slas",serviceSLA.listarUsuario(idSla));
		model.addAttribute("login", usuario);
		return "chamado/crud";
	}

	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public String editChamado(Model model, @PathVariable("id") Long id, HttpSession session) {
		Long idSla = null;
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("chamado",serviceChamado.buscaPorId(id));
		model.addAttribute("filas",serviceFila.listar());
		if(usuario.getSla() == null)
			idSla = 1L;
		else {
			idSla = usuario.getSla().getId();
		}
		model.addAttribute("slas",serviceSLA.listarUsuario(idSla));
		model.addAttribute("login", usuario);
		return "chamado/crud";
	}

	@RequestMapping(value="/atender/{id}",method = RequestMethod.GET)
	public String atendeChamado(Model model, @PathVariable("id") Long id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		Long idSla = null;
		Chamado chamado = serviceChamado.buscaPorId(id);

		model.addAttribute("chamado",chamado);
		model.addAttribute("filas",serviceFila.listar());
		if(usuario.getSla() == null)
			idSla = 1L;
		else {
			idSla = usuario.getSla().getId();
		}
		model.addAttribute("slas",serviceSLA.listarUsuario(idSla));
		model.addAttribute("login", usuario);
		return "chamado/atende";
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String deleteChamado(Model model, @PathVariable("id") Long id, HttpSession session) {
		Chamado chamado = serviceChamado.buscaPorId(id);
		serviceChamado.remover(chamado);
		Usuario solicitante = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("chamados",serviceChamado.listarSolucionador(solicitante));
		return "redirect:/chamados";
	}
	
	@RequestMapping(value="/crudChamado",method = RequestMethod.POST)
	public String crudChamado(@Valid Chamado chamado, BindingResult result, Model model, HttpSession session) {
		Long idSla = null;
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");		
		if (!result.hasErrors()) {
			if(chamado.getId() == null) {
				chamado.setSolicitante(usuario);
				serviceChamado.adicionar(chamado);
			} else {
				serviceChamado.alterar(chamado);
			}
			return "redirect:/chamados";
		} else {
			model.addAttribute("chamado",chamado);
			model.addAttribute("filas",serviceFila.listar());
			if(usuario.getSla() == null)
				idSla = 1L;
			else {
				idSla = usuario.getSla().getId();
			}
			model.addAttribute("slas",serviceSLA.listarUsuario(idSla));
			System.out.println("erro "+result.toString());
			return "chamado/crud";
		}
	}

	@RequestMapping(value="/atenderChamado",method = RequestMethod.POST)
	public String crudAtender(Chamado chamado) {

		serviceChamado.atender(chamado);
		return "redirect:/chamados";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Fila.class, filaPropertyEditor);
		webDataBinder.registerCustomEditor(SLA.class, slaPropertyEditor);
	}
}
