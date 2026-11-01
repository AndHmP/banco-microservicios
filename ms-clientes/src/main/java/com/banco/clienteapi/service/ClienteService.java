package com.banco.clienteapi.service;

import com.banco.clienteapi.entity.Cliente;

import java.util.List;

public interface ClienteService {


    List<Cliente> listarTodos();

    Cliente obtenerPorId(Long id);

    Cliente crearCliente(Cliente cliente);

    Cliente actualizarCliente(Long id, Cliente cliente);

    void eliminarCliente(Long id);
}
