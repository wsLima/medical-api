package br.com.wsystechnologies.medical.exceptions;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
