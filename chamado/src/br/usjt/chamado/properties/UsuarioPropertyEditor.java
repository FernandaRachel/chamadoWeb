package br.usjt.chamado.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.chamado.dao.UsuarioDAO;
import br.usjt.chamado.model.Usuario;

@Component
public class UsuarioPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private UsuarioDAO daoUsuario;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Usuario usuario;
		try {
			id = Long.parseLong(text);
			usuario = daoUsuario.buscaPorId(id);
		} catch (Exception e) {
			usuario = null;
		}
		setValue(usuario);
	}
}
