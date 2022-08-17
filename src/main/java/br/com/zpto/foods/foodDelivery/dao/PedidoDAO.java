package br.com.zpto.foods.foodDelivery.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zpto.foods.foodDelivery.entity.Entrega;
import br.com.zpto.foods.foodDelivery.entity.Pedido;

public interface PedidoDAO extends PagingAndSortingRepository<Pedido, Long> {
	
	Pedido findPedidoById(long id);
    Page findAll(Pageable pageable);
    
}
