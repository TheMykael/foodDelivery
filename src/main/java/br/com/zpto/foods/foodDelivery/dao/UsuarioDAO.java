package br.com.zpto.foods.foodDelivery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zpto.foods.foodDelivery.entity.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
	
	//@Query("SELECT u FROM Usuario u WHERE u.nome = ?1")
	Usuario findByNome(String nome);
	//@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
	Usuario findByUsername(String username);

}
