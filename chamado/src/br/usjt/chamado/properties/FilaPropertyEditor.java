package br.usjt.chamado.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.chamado.dao.FilaDAO;
import br.usjt.chamado.model.Fila;

@Component
public class FilaPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private FilaDAO daoFila;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Fila fila;
		try {
			id = Long.parseLong(text);
			fila = daoFila.buscaPorId(id);
		} catch (Exception e) {
			fila = null;
		}
		setValue(fila);
	}
}
