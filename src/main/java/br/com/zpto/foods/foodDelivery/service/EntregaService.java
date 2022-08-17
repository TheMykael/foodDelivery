package br.com.zpto.foods.foodDelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zpto.foods.foodDelivery.dao.EntregaDAO;
import br.com.zpto.foods.foodDelivery.entity.Entrega;

@Service
public class EntregaService {
	
	@Autowired
	private EntregaDAO entregaDAO;
	
	public EntregaService() {
		
	}

	public Entrega criarEntrega(Entrega entrega) {
        return entregaDAO.save(entrega);
    }

    public Entrega obterEntregaPorId(long id) {
        return entregaDAO.findEntregaById(id);
    }

    public void atualizarEntrega(Entrega entrega) {
        entregaDAO.save(entrega);
    }

    public void deletarEntrega(Long id) {
        entregaDAO.deleteById(id);
    }

    public Iterable<Entrega> obterTodasEntregas() {
        return entregaDAO.findAll();
    }

}
