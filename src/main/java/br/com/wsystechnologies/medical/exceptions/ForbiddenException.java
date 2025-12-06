package br.com.wsystechnologies.medical.exceptions;

public class ForbiddenException extends DomainException {

    public ForbiddenException(String message) {
        super(message);
    }
}
