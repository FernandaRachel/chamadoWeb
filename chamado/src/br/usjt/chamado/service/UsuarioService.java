package br.usjt.chamado.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.chamado.dao.UsuarioDAO;
import br.usjt.chamado.model.TipoUsuario;
import br.usjt.chamado.model.Usuario;

@Service
public class UsuarioService {

	private UsuarioDAO daoUsuario;

	@Autowired
	public UsuarioService(UsuarioDAO daoUsuario) {
		this.daoUsuario = daoUsuario;
	}
	
	public void adicionar(Usuario usuario) {
		if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
			usuario.setFila(null);
			usuario.setSla(null);;
		}
		if (usuario.getTipoUsuario() == TipoUsuario.SOLUCIONADOR) {
			usuario.setSla(null);;
		}		
		if (usuario.getTipoUsuario() == TipoUsuario.SOLICITANTE) {
			usuario.setFila(null);;
		}		
		daoUsuario.adicionar(usuario);
	}
	
	public void alterar(Usuario usuario, HttpSession session) {
		Usuario usuarioOld = buscaPorId(usuario.getId());
		Usuario login = (Usuario) session.getAttribute("usuarioLogado");
		if (usuario.equals(login)) {
			session.setAttribute("usuarioLogado", usuario);
		}
		
		if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
			usuario.setFila(null);
			usuario.setSla(null);
		}
		if (usuario.getTipoUsuario() == TipoUsuario.SOLUCIONADOR) {
			usuario.setSla(null);
			usuario.setAtivo(usuarioOld.getAtivo());
		}
		if (usuario.getTipoUsuario() == TipoUsuario.SOLICITANTE) {
			usuario.setFila(null);
			usuario.setAtivo(usuarioOld.getAtivo());
		}
		daoUsuario.alterar(usuario);
	}
	
	public void remover(Usuario usuario) {
		daoUsuario.remover(usuario);
	}
	
	public Usuario buscaPorId(Long id) {
		return daoUsuario.buscaPorId(id);
	}
	
	public List<Usuario> listar() {
		return daoUsuario.listar();
	}
	
	public Usuario buscaPorLogin(String login) {
		return daoUsuario.buscaPorLogin(login);
	}

	public List<Usuario> listarSolucionador() {
		return daoUsuario.listarSolucionador();
	}
}