package br.com.zpto.foods.foodDelivery.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import br.com.zpto.foods.foodDelivery.exception.DadoMalformatadoException;
import br.com.zpto.foods.foodDelivery.exception.RecursoNaoEncontradoException;
import br.com.zpto.foods.foodDelivery.exception.RestException;

public class AbstractRestHandler implements ApplicationEventPublisherAware {
	
	protected ApplicationEventPublisher eventPublisher;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DadoMalformatadoException.class)
    public
    @ResponseBody
    RestException dadoMalformatadoMapper(DadoMalformatadoException ex, WebRequest request, HttpServletResponse response) {
        return new RestException(ex, "O dado não está em um formato válido.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public
    @ResponseBody
    RestException recursoNaoEncontradoMapper(RecursoNaoEncontradoException ex, WebRequest request, HttpServletResponse response) {
        return new RestException(ex, "Recurso não encontrado.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public static <T> T verificarRecursoEncontrado(final T resource) {
        if (resource == null) {
            throw new RecursoNaoEncontradoException("Recurso não encontrado");
        }
        return resource;
    }

}
