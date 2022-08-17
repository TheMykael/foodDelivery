package br.com.zpto.foods.foodDelivery.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "entrega")
public class Entrega {
	
	@Id
	@SequenceGenerator(name = "delivery_id_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_generator")
    private long id;
	
	@Column(nullable = false)
    private BigDecimal taxa;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties("entrega")
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Entrega() {
    	
    }
    
    public Entrega(BigDecimal taxa, List<Pedido> pedidos) {
        this.taxa = taxa;
        this.pedidos = pedidos;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getTaxa() {
		return taxa;
	}

	public void setTaxa(BigDecimal taxa) {
		this.taxa = taxa;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public void addPedidoToEntrega(Pedido pedido) {
		pedido.setEntrega(this);
    }

}
