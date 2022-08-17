package br.com.zpto.foods.foodDelivery.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zpto.foods.foodDelivery.entity.Cliente;
import br.com.zpto.foods.foodDelivery.exception.DadoMalformatadoException;
import br.com.zpto.foods.foodDelivery.service.ClienteService;

@RestController
@RequestMapping("/clientes")
//TODO VER COMO FICA NO SPRING A QUESTAO DO PERMIT ALL
//@PermitAll
public class ClienteRestController extends AbstractRestHandler {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void criarCliente(@RequestBody Cliente cliente, HttpServletRequest request, HttpServletResponse response) {
		Cliente clienteCriada = this.clienteService.criarCliente(cliente);
		response.setHeader("Destino", request.getRequestURL().append("/").append(clienteCriada.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Iterable<Cliente> obterTodosClientes(HttpServletRequest request,
			HttpServletResponse response) {
		return this.clienteService.obterTodosClientes();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Cliente obterClientePorId(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.clienteService.obterClientePorId(id));
		return clienteService.obterClientePorId(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	// @RolesAllowed({ "ADMIN", "SUPERVISOR" })
	public void atualizarCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.clienteService.obterClientePorId(id));
		if (id != cliente.getId())
			throw new DadoMalformatadoException("ID não reconhecido. Especifique-o corretamente.");
		this.clienteService.atualizarCliente(cliente);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	// @RolesAllowed({ "ADMIN" })
	public void deletarCliente(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		verificarRecursoEncontrado(this.clienteService.obterClientePorId(id));
		clienteService.deletarCliente(id);
	}

}
