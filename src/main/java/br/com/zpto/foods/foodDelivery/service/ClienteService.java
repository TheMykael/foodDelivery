package br.com.zpto.foods.foodDelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zpto.foods.foodDelivery.dao.ClienteDAO;
import br.com.zpto.foods.foodDelivery.entity.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;

	public ClienteService() {

	}

	public Cliente criarCliente(Cliente cliente) {
		return clienteDAO.save(cliente);
	}

	public Cliente obterClientePorId(long id) {
		return clienteDAO.findClienteById(id);
	}

	public void atualizarCliente(Cliente cliente) {
		clienteDAO.save(cliente);
	}

	public void deletarCliente(Long id) {
		clienteDAO.deleteById(id);
	}

	public Iterable<Cliente> obterTodosClientes() {
		return clienteDAO.findAll();
	}

}
