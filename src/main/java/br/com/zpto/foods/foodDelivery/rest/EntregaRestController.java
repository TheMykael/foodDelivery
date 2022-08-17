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

import br.com.zpto.foods.foodDelivery.entity.Entrega;
import br.com.zpto.foods.foodDelivery.exception.DadoMalformatadoException;
import br.com.zpto.foods.foodDelivery.service.EntregaService;

@RestController
@RequestMapping("/entregas")
//TODO VER COMO FICA NO SPRING A QUESTAO DO PERMIT ALL
//@PermitAll
public class EntregaRestController extends AbstractRestHandler {

	@Autowired
	private EntregaService entregaService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void criarEntrega(@RequestBody Entrega entrega, HttpServletRequest request, HttpServletResponse response) {
		Entrega entregaCriada = this.entregaService.criarEntrega(entrega);
		response.setHeader("Destino", request.getRequestURL().append("/").append(entregaCriada.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Iterable<Entrega> obterTodasEntregas(HttpServletRequest request,
			HttpServletResponse response) {
		return this.entregaService.obterTodasEntregas();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Entrega obterEntregaPorId(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.entregaService.obterEntregaPorId(id));
		return entregaService.obterEntregaPorId(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	//@RolesAllowed({ "ADMIN", "SUPERVISOR" })
	public void atualizarEntrega(@PathVariable("id") Long id, @RequestBody Entrega entrega, HttpServletRequest request,
			HttpServletResponse response) {
		verificarRecursoEncontrado(this.entregaService.obterEntregaPorId(id));
		if (id != entrega.getId())
			throw new DadoMalformatadoException("ID não reconhecido. Especifique-o corretamente.");
		this.entregaService.atualizarEntrega(entrega);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// TODO VER COMO FICA NO SPRING A QUESTAO DOS ROLES
	//@RolesAllowed({ "ADMIN" })
	public void deletarEntrega(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		verificarRecursoEncontrado(this.entregaService.obterEntregaPorId(id));
		entregaService.deletarEntrega(id);
	}

}
