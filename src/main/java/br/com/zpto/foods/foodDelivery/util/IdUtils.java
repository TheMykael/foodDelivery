package br.com.zpto.foods.foodDelivery.util;

public class IdUtils {

	public static Long idValido(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido. Deve ser número inteiro maior que zero.");
        }
        return id;
    }
}
