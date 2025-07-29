package com.techlab.proyecto_final.exception;

public class StockInsuficienteException extends RuntimeException {
  public StockInsuficienteException(String mensaje) {
    super(mensaje);
  }
}
