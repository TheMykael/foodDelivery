package br.com.zpto.foods.foodDelivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@SequenceGenerator(name = "delivery_id_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_generator")
    private long id;
	
	@Column(nullable = false)
    private String descricao;
	
    @ManyToOne
    @JoinColumn(name = "id_cliente_fk" , nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties({"nome", "cpf", "pedidos"})
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_entrega_fk", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties({"taxa", "pedidos"})
    private Entrega entrega;

    public Pedido() {
    	
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

}
