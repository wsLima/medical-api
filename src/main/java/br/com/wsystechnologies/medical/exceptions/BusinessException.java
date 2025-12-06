package br.com.wsystechnologies.medical.exceptions;

public class BusinessException extends DomainException {

    public BusinessException(String message) {
        super(message);
    }
}
