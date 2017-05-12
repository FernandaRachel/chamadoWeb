package br.usjt.chamado.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.chamado.dao.SLADAO;
import br.usjt.chamado.model.SLA;

@Service
public class SLAService {

	private SLADAO daoSLA;

	@Autowired
	public SLAService(SLADAO daoSLA) {
		this.daoSLA = daoSLA;
	}
	
	public SLA buscaPorId(Long id) {
		return daoSLA.buscaPorId(id);
	}
	
	public List<SLA> listar() {
		return daoSLA.listar();
	}
	
	public SLA buscaPorDescricao(String descricao) {
		return daoSLA.buscaPorDescricao(descricao);
	}

	public List<SLA> listarUsuario(Long id) {
		// TODO Auto-generated method stub
		return daoSLA.buscaPorUsuario(id);
	}
	
	public String calculaPrazo(Date dtLimite) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime limite;
        Instant instant;
        String dias = "",horas = "",minutos = "";
        
		instant = Instant.ofEpochMilli(dtLimite.getTime());
		limite = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		
        long diferencaEmMinutos = ChronoUnit.MINUTES.between(hoje, limite);
        long diferencaEmHours = ChronoUnit.HOURS.between(hoje, limite);
        long diferencaEmDias = ChronoUnit.DAYS.between(hoje, limite);	
        
        if (diferencaEmHours%60  == 24) diferencaEmHours = 0;
        
        if (diferencaEmDias != 0) {
           dias = diferencaEmDias + " dia(s) ";
        } 
        if (diferencaEmHours%60 != 0) {
           horas = diferencaEmHours%60 + " hora(s) ";
        } 
        if (diferencaEmMinutos%60 != 0) {
           minutos = diferencaEmMinutos%60 + " minuto(s) ";
        }
        return dias + horas + minutos;
	}
	
	public boolean estaAtrasado(Date dtLimite) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime limite;
        Instant instant;		
		instant = Instant.ofEpochMilli(dtLimite.getTime());
		limite = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        long diferencaEmMinutos = ChronoUnit.MINUTES.between(hoje, limite);    
        
        return diferencaEmMinutos < 0;
	}
}