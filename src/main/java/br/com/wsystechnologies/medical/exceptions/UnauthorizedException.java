package br.com.wsystechnologies.medical.exceptions;

public class UnauthorizedException extends DomainException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
