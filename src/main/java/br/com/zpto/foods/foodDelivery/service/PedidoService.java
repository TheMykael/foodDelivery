package br.com.zpto.foods.foodDelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zpto.foods.foodDelivery.dao.PedidoDAO;
import br.com.zpto.foods.foodDelivery.entity.Pedido;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoDAO pedidoDAO;
	
	public PedidoService() {
		
	}

	public Pedido criarPedido(Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    public Pedido obterPedidoPorId(long id) {
        return pedidoDAO.findPedidoById(id);
    }

    public void atualizarPedido(Pedido pedido) {
    	pedidoDAO.save(pedido);
    }

    public void deletarPedido(Long id) {
    	pedidoDAO.deleteById(id);
    }

    public Iterable<Pedido> obterTodosPedidos() {
        return pedidoDAO.findAll();
    }

}
