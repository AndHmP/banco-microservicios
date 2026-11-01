package com.banco.clienteapi.dto;

import com.banco.clienteapi.entity.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
        String apellido,

        @NotBlank(message = "El dni es obligatorio")
        @Size(max = 8, min = 8, message = "El dni debe tener 8 digitos")
        @Pattern(regexp = "[0-9]+", message = "El dni solo debe contener números")
        String dni,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email
) {

    public Cliente toEntity(){
        return new Cliente(null,nombre,apellido, dni, email);
    }
}
