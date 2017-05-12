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
import br.usjt.chamado.model.SLA;
import br.usjt.chamado.model.Fila;
import br.usjt.chamado.model.TipoUsuario;
import br.usjt.chamado.model.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/spring-context.xml")
@TransactionConfiguration(defaultRollback = true)
public class UsuarioDAOTest {

	@Autowired
	UsuarioDAO usuarioDAO;

	@Autowired
	FilaDAO filaDAO;

	@Autowired
	SLADAO slaDAO;

	@Transactional
	@Test
	public void salvarTest() {
		Usuario usuario = new Usuario();
		Fila fila = new Fila();

		fila.setDescricao("teste");

		Fila filaSalva = filaDAO.buscaPorId((long) 1);

		SLA sla = new SLA();

		sla.setDescricao("nivel 1");

		SLA slaSalva = slaDAO.buscaPorId((long) 1);

		usuario.setSla(slaSalva);
		usuario.setFila(filaSalva);
		usuario.setCelular("11 111111111");
		usuario.setEmail("teste@teste.com");
		usuario.setCpf("111.111.111-11");
		usuario.setLogin("teste_login");
		usuario.setNome("teste_nome");
		usuario.setSenha("123456");
		usuario.setConfirmaSenha("123456");
		usuario.setAtivo(1);
		usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

		usuarioDAO.adicionar(usuario);
		Usuario fixure = usuarioDAO.buscaPorId(usuario.getId());

		Assert.assertNotNull(fixure);
	}

	@Transactional
	@Test
	public void removerTest() {
		Usuario usuario = new Usuario();
		Fila fila = new Fila();
		SLA sla = new SLA();

		fila.setDescricao("teste_fila");
		sla.setDescricao("nivel 1");

		Fila filaSalva = filaDAO.buscaPorId((long) 1);
		SLA slaSalva = slaDAO.buscaPorId((long) 1);

		usuario.setSla(slaSalva);
		usuario.setFila(filaSalva);
		usuario.setCelular("11 111111111");
		usuario.setEmail("teste@teste.com");
		usuario.setCpf("111.111.111-11");
		usuario.setLogin("teste_login");
		usuario.setNome("teste_nome");
		usuario.setSenha("123456");
		usuario.setConfirmaSenha("123456");
		usuario.setAtivo(1);
		usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

		usuarioDAO.adicionar(usuario);
		Long id = usuario.getId();

		Usuario usuarioSalvo = usuarioDAO.buscaPorId(id);
		usuarioDAO.remover(usuarioSalvo);

		Usuario usuarioExcluido = usuarioDAO.buscaPorId(id);

		Assert.assertNull(usuarioExcluido);

	}
}