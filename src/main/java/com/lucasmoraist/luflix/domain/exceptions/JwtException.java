package com.lucasmoraist.luflix.domain.exceptions;

public class JwtException extends RuntimeException {

    public JwtException(String message, Throwable ex) {
        super(message, ex);
    }

}
