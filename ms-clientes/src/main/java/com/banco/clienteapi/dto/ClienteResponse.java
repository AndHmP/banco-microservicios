package com.banco.clienteapi.dto;

import com.banco.clienteapi.entity.Cliente;

public record ClienteResponse(
        Long id,
        String nombre,
        String apellido,
        String dni,
        String email
) {

    public static ClienteResponse fromEntity(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni(),
                cliente.getEmail()
        );
    }
}