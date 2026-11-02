package com.banco.cuentas.dto;

import com.banco.cuentas.entity.Cuenta;
import com.banco.cuentas.entity.TipoCuenta;

import java.math.BigDecimal;

public record CuentaResponse(
        Long id,
        String numeroCuenta,
        TipoCuenta tipo,
        BigDecimal saldo,
        Long clienteId
) {
    public static CuentaResponse fromEntity(Cuenta cuenta) {
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipo(),
                cuenta.getSaldo(),
                cuenta.getClienteId()
        );
    }
}
