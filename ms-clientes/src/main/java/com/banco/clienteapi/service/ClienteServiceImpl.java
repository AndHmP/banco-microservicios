package com.banco.clienteapi.service;

import com.banco.clienteapi.entity.Cliente;
import com.banco.clienteapi.exception.RecursoDuplicadoException;
import com.banco.clienteapi.exception.RecursoNoEncontradoException;
import com.banco.clienteapi.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no encontrado"));
    }

    @Override
    public Cliente obtenerPorDni(String dni) {
        return clienteRepository.findByDni(dni).orElseThrow(
                () -> new RecursoNoEncontradoException("No hay ningún cliente con el Dni: "+ dni)
        );
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        if(clienteRepository.existsByDni(cliente.getDni()))  {
            throw new RecursoDuplicadoException("Ya existe un cliente con DNI "+cliente.getDni());
        }
        if(clienteRepository.existsByEmail(cliente.getEmail())){
            throw new RecursoDuplicadoException("Ya existe un cliente con email "+ cliente.getEmail());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente clienteExistente =  obtenerPorId(id);

        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setEmail(cliente.getEmail());

        return clienteRepository.save(clienteExistente);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente clienteExistente =  obtenerPorId(id);
        clienteRepository.delete(clienteExistente);
    }
}
