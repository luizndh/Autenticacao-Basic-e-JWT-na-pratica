package com.luiz.learning.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luiz.learning.dto.ErroDTO;
import com.luiz.learning.exception.NotaNaoEncontradaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de Not Found
    @ExceptionHandler(NotaNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDTO handleResourceNotFound(NotaNaoEncontradaException ex) {
        return new ErroDTO(
            HttpStatus.NOT_FOUND.value(),
            "Nota não encontrada.",
            ex.getMessage()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDTO handleResourceNotFound(UsernameNotFoundException ex) {
        return new ErroDTO(
            HttpStatus.NOT_FOUND.value(),
            "Usuário não encontrado.",
            ex.getMessage()
        );
    }

    // Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroDTO handleValidationErrors(MethodArgumentNotValidException ex) {

        String erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ErroDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            erros
        );
    }

    // Erros gerais
    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ErroDTO handleGeneralException(Exception ex) {

    //     return new ErroDTO(
    //         HttpStatus.INTERNAL_SERVER_ERROR.value(),
    //         "Erro interno do servidor",
    //         "Ocorreu um erro inesperado. Contate o suporte."
    //     );
    // }

    // Erro de autenticação
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroDTO handleUnauthorizedException(AccessDeniedException ex) {

        return new ErroDTO(
            HttpStatus.FORBIDDEN.value(),
            "Usuário não autorizado",
            "Esse usuário não possui a permissão para acessar esse serviço."
        );
    }
}
