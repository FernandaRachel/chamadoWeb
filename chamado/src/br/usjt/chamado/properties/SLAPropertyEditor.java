package br.usjt.chamado.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.chamado.dao.SLADAO;
import br.usjt.chamado.model.SLA;

@Component
public class SLAPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private SLADAO daoSLA;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		SLA sla;
		try {
			id = Long.parseLong(text);
			sla = daoSLA.buscaPorId(id);
		} catch (Exception e) {
			sla = null;
		}
		setValue(sla);
	}
}
