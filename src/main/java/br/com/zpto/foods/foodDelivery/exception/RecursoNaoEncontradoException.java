package br.com.zpto.foods.foodDelivery.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4889254346257482594L;

	public RecursoNaoEncontradoException() {
        super();
    }

    public RecursoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(Throwable causa) {
        super(causa);
    }

}
