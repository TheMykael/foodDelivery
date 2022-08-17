package br.com.zpto.foods.foodDelivery.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zpto.foods.foodDelivery.entity.Cliente;

public interface ClienteDAO extends PagingAndSortingRepository<Cliente, Long> {
	
	Cliente findClienteById(long id);
    Page findAll(Pageable pageable);

}
