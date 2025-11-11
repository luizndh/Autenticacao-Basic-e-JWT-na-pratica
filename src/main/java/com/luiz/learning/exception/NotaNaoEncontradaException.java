package com.luiz.learning.exception;

public class NotaNaoEncontradaException extends RuntimeException {

    public NotaNaoEncontradaException(Long id) {
        super("Nota com id " + id + " não encontrada.");
    }
}
