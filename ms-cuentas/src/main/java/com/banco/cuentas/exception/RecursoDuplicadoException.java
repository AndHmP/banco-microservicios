package com.banco.cuentas.exception;

public class RecursoDuplicadoException extends RuntimeException{

    public RecursoDuplicadoException(String mensaje) {
       super(mensaje);
    }
}
