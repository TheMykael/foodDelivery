package br.com.zpto.foods.foodDelivery.exception;

public class DadoMalformatadoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5945659505410983069L;

	public DadoMalformatadoException() {
        super();
    }

    public DadoMalformatadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public DadoMalformatadoException(String mensagem) {
        super(mensagem);
    }

    public DadoMalformatadoException(Throwable causa) {
        super(causa);
    }

}
