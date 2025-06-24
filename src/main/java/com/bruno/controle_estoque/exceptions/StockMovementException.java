package com.bruno.controle_estoque.exceptions;

public class StockMovementException extends RuntimeException {
    public StockMovementException(String message) {
        super(message);
    }
}
