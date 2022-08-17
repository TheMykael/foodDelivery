package br.com.zpto.foods.foodDelivery.exception;

public class RestException {

	public final String detalhe;
    public final String mensagem;

    public RestException(Exception ex, String detalhe) {
        this.mensagem = ex.getLocalizedMessage();
        this.detalhe = detalhe;
    }
    
}
