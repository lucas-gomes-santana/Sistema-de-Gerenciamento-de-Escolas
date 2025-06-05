package com.br.api.exception;

public class InvalidCredentialException extends Throwable{
    public InvalidCredentialException(String message) {
        super(message);
    }
}