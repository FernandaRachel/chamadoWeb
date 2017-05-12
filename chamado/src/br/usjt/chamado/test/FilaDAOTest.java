package br.usjt.chamado.test;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import br.usjt.chamado.dao.FilaDAO;
import br.usjt.chamado.model.Fila;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/spring-context.xml")
@TransactionConfiguration(defaultRollback = true)

public class FilaDAOTest {

	@Autowired
	FilaDAO filaDAO;

	@Transactional
	@Test
	public void salvarTest() {
		Fila fila = new Fila();

		fila.setId((long) 4);
		fila.setDescricao("Fila 1");
		fila.setAtivo(1);

		filaDAO.adicionar(fila);

		Fila fixure = filaDAO.buscaPorId(fila.getId());
		Assert.assertNotNull(fixure);
	}

	@Transactional
	@Test
	public void excluirTest() {
		Fila fila = new Fila();

		fila.setId((long) 5);
		fila.setDescricao("Fila 2");
		fila.setAtivo(1);

		filaDAO.adicionar(fila);

		Long id = fila.getId();

		Fila remocao = new Fila();
		remocao.setId(id);
		filaDAO.remover(remocao);

		Fila filaExcluida = filaDAO.buscaPorId(id);

		Assert.assertNull(filaExcluida);

	}
}