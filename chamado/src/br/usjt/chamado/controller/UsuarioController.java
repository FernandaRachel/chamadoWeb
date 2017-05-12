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

import br.usjt.chamado.model.Fila;
import br.usjt.chamado.model.SLA;
import br.usjt.chamado.model.Usuario;
import br.usjt.chamado.properties.FilaPropertyEditor;
import br.usjt.chamado.properties.SLAPropertyEditor;
import br.usjt.chamado.service.FilaService;
import br.usjt.chamado.service.SLAService;
import br.usjt.chamado.service.UsuarioService;

@Controller
@Transactional
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private UsuarioService serviceUsuario;
	private FilaService serviceFila;
	private SLAService serviceSLA;
	private FilaPropertyEditor filaPropertyEditor;
	private SLAPropertyEditor slaPropertyEditor;
	
	@Autowired
	public UsuarioController(UsuarioService serviceUsuario, FilaPropertyEditor filaPropertyEditor, FilaService serviceFila,SLAService serviceSLA, 
			SLAPropertyEditor slaPropertyEditor ) {
		this.serviceUsuario = serviceUsuario;
		this.filaPropertyEditor = filaPropertyEditor;
		this.serviceFila = serviceFila;
		this.serviceSLA = serviceSLA;
		this.slaPropertyEditor = slaPropertyEditor;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listaTodos(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("usuarios",serviceUsuario.listar());
		model.addAttribute("login", usuario);
		return "usuario/lista";
	}
	
	@RequestMapping(value="/novo")
	public String addUsuario(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("usuario",new Usuario());
		model.addAttribute("filas",serviceFila.listar());
		model.addAttribute("slas",serviceSLA.listar());
		model.addAttribute("login", usuario);
		return "usuario/crud";
	}

	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public String editUsuario(Model model, @PathVariable("id") Long id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("usuario",serviceUsuario.buscaPorId(id));
		model.addAttribute("filas",serviceFila.listar());
		model.addAttribute("slas",serviceSLA.listar());
		model.addAttribute("login", usuario);
		return "usuario/crud";
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String deleteUsuario(Model model, @PathVariable("id") Long id) {
		Usuario usuario = serviceUsuario.buscaPorId(id);
		serviceUsuario.remover(usuario);
		model.addAttribute("usuarios",serviceUsuario.listar());
		return "redirect:/usuarios";
	}
	
	@RequestMapping(value="/crudUsuario",method = RequestMethod.POST)
	public String crudUsuario(@Valid Usuario usuario, BindingResult result, Model model, HttpSession session) {
		if (!result.hasErrors()) {
			if(usuario.getId() == null) {
				serviceUsuario.adicionar(usuario);
			} else {
				serviceUsuario.alterar(usuario, session);
			}
			return "redirect:/usuarios";
		} else {
			System.out.println(result.toString());
			model.addAttribute("usuario",usuario);
			model.addAttribute("filas",serviceFila.listar());
			model.addAttribute("slas",serviceSLA.listar());			return "usuario/crud";
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Fila.class, filaPropertyEditor);
		webDataBinder.registerCustomEditor(SLA.class, slaPropertyEditor);
	}
}
