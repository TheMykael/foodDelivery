package br.com.zpto.foods.foodDelivery.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zpto.foods.foodDelivery.entity.Entrega;

public interface EntregaDAO extends PagingAndSortingRepository<Entrega, Long> {
	
	Entrega findEntregaById(long id);
    Page findAll(Pageable pageable);

}
