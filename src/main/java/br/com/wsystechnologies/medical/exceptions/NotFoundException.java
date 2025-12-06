package br.com.wsystechnologies.medical.exceptions;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }
}
