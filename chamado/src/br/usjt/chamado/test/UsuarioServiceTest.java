package br.usjt.chamado.test;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import br.usjt.chamado.dao.SLADAO;
import br.usjt.chamado.dao.FilaDAO;
import br.usjt.chamado.dao.UsuarioDAO;
import br.usjt.chamado.exception.ServiceException;
import br.usjt.chamado.model.Fila;
import br.usjt.chamado.model.SLA;
import br.usjt.chamado.model.TipoUsuario;
import br.usjt.chamado.model.Usuario;
import br.usjt.chamado.service.UsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/spring-context.xml")
@TransactionConfiguration(defaultRollback = true)
public class UsuarioServiceTest {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UsuarioDAO usuarioDAO;

	@Autowired
	FilaDAO filaDAO;

	@Autowired
	SLADAO slaDAO;

	@Transactional
	@Test(expected = ServiceException.class)
	public void naoDeveSalvarTest() throws ServiceException {
		Usuario usuario = new Usuario();
		Fila fila = new Fila();
		SLA sla = new SLA();

		fila.setDescricao("teste_fila");
		sla.setDescricao("teste_sla");

		filaDAO.adicionar(fila);
		SLA slaSalva = slaDAO.buscaPorId((long) 1);

		usuario.setFila(fila);
		usuario.setCelular("11 111111111");
		usuario.setEmail("teste@teste.com");
		usuario.setCpf("111.111.111-11");
		usuario.setLogin("teste_login");
		usuario.setNome("teste_nome");
		usuario.setSenha("123456");
		usuario.setSla(slaSalva);
		usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

		usuarioDAO.adicionar(usuario);

		usuarioService.adicionar(usuario);
	}

	@Transactional
	@Test
	public void deveSalvarTest() throws ServiceException {
		Usuario usuario = new Usuario();
		Fila fila = new Fila();
		SLA sla = new SLA();

		fila.setDescricao("teste_fila");
		sla.setDescricao("teste_sla");

		filaDAO.adicionar(fila);
		SLA slaSalva = slaDAO.buscaPorId((long) 2);

		usuario.setFila(fila);
		usuario.setCelular("11 111111111");
		usuario.setEmail("teste@teste.com");
		usuario.setCpf("111.111.111-11");
		usuario.setLogin("teste_login");
		usuario.setNome("teste_nome");
		usuario.setSenha("123456");
		usuario.setSla(slaSalva);
		usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

		usuarioService.adicionar(usuario);
		Usuario fixure = usuarioDAO.buscaPorId(usuario.getId());
		Assert.assertNotNull(fixure);
	}
}