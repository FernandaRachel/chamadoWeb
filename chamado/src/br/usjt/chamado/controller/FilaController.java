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
import br.usjt.chamado.model.Usuario;
import br.usjt.chamado.properties.UsuarioPropertyEditor;
import br.usjt.chamado.service.FilaService;
import br.usjt.chamado.service.UsuarioService;

@Controller
@Transactional
@RequestMapping("/filas")
public class FilaController {
	
	private UsuarioService serviceUsuario;
	private FilaService serviceFila;
	private UsuarioPropertyEditor usuarioPropertyEditor;
	
	@Autowired
	public FilaController(UsuarioService serviceUsuario, FilaService serviceFila, UsuarioPropertyEditor usuarioPropertyEditor) {
		this.serviceUsuario = serviceUsuario;
		this.serviceFila = serviceFila;
		this.usuarioPropertyEditor = usuarioPropertyEditor;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listaTodos(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("filas",serviceFila.listar());
		model.addAttribute("login", usuario);
		return "fila/lista";
	}
	
	@RequestMapping(value="/novo")
	public String addFila(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("gerentes",serviceUsuario.listarSolucionador());
		model.addAttribute("fila",new Fila());
		model.addAttribute("login", usuario);
		return "fila/crud";
	}

	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public String editFila(Model model, @PathVariable("id") Long id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("fila",serviceFila.buscaPorId(id));
		model.addAttribute("gerentes",serviceUsuario.listarSolucionador());
		model.addAttribute("login", usuario);
		return "fila/crud";
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String deleteFila(Model model, @PathVariable("id") Long id) {
		Fila fila = serviceFila.buscaPorId(id);
		serviceFila.remover(fila);
		model.addAttribute("filas",serviceFila.listar());
		return "redirect:/filas";
	}
	
	@RequestMapping(value="/crudFila",method = RequestMethod.POST)
	public String crudFila(@Valid Fila fila, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			if(fila.getId() == null) {
				serviceFila.adicionar(fila);
			} else {
				serviceFila.alterar(fila);
			}
			return "redirect:/filas";
		} else {
			model.addAttribute("fila",fila);
			return "fila/crud";
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Usuario.class, usuarioPropertyEditor);
	}
	
}
