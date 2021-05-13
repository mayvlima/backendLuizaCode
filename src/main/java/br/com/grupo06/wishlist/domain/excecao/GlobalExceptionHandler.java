package br.com.grupo06.wishlist.domain.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value= HttpStatus.EXPECTATION_FAILED, reason = "ERRO ESPERADO")
    @ExceptionHandler(ExcecaoEsperada.class)
    public String handleExcecaoEsperada(HttpServletRequest request, ExcecaoEsperada ex){
        return ex.getMessage();
    }
}
