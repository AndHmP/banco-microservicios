package com.banco.cuentas.dto;

import com.banco.cuentas.entity.Cuenta;
import com.banco.cuentas.entity.TipoCuenta;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CuentaRequest(

        @NotBlank(message = "El número de cuenta debe definirse.")
        @Size(max = 20, message = "El número de cuenta debe tener máximo 20 caracteres.")
        String numeroCuenta,

        @NotNull(message = "El tipo de cuenta debe definirse")
        TipoCuenta tipo,

        @NotNull(message = "El saldo de la cuenta debe definirse")
        @PositiveOrZero(message = "El saldo de la cuenta no puede ser menor a 0")
        BigDecimal saldo,

        @NotNull(message = "El id del cliente debe definirse")
        @Positive(message = "El id no puede ser igual o menor a 0")
        Long clienteId
) {

    public Cuenta toEntity() {
        return new Cuenta(null, numeroCuenta, tipo, saldo, clienteId);
    }
}
