package br.com.shiroshima.exception;

public class AttributeNotBlankException extends RuntimeException {
    public AttributeNotBlankException(String message) {
        super(message);
    }
}
