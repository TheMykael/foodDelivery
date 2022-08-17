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

import br.com.zpto.foods.foodDelivery.entity.Pedido;
import br.com.zpto.foods.foodDelivery.exception.DadoMalformatadoException;
import br.com.zpto.foods.foodDelivery.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
//TODO VER COMO FICA NO SPRING A QUESTAO DO PERMIT ALL
//@PermitAll
public class PedidoRestController extends AbstractRestHandler {

	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void criarPedido(@RequestBody Pedido pedido, HttpServletRequest request, HttpServletResponse response) {
		Pedido pedidoCriada = this.pedidoService.criarPedido(pedido);
		response.setHeader("Destino", request.getRequestURL().append("/").append(pedidoCriada.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Iterable<Pedido> obterTodasPedidos(HttpServletRequest request,
			HttpServletResponse response) {
		return this.pedidoService.obterTodosPedidos();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Pedido obterPedidoPorId(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.pedidoService.obterPedidoPorId(id));
		return pedidoService.obterPedidoPorId(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	//@RolesAllowed({ "ADMIN", "SUPERVISOR" })
	public void atualizarPedido(@PathVariable("id") Long id, @RequestBody Pedido pedido, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.pedidoService.obterPedidoPorId(id));
		if (id != pedido.getId())
			throw new DadoMalformatadoException("ID não reconhecido. Especifique-o corretamente.");
		this.pedidoService.atualizarPedido(pedido);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	//@RolesAllowed({ "ADMIN" })
	public void deletarPedido(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		verificarRecursoEncontrado(this.pedidoService.obterPedidoPorId(id));
		pedidoService.deletarPedido(id);
	}

}
