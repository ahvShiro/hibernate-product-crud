package br.com.shiroshima.exception;

public class PriceNotNegativeException extends RuntimeException {
    public PriceNotNegativeException(String message) {
        super(message);
    }
}
