package com.banco.cuentas.service;

import com.banco.cuentas.client.ClienteClient;
import com.banco.cuentas.entity.Cuenta;
import com.banco.cuentas.exception.RecursoDuplicadoException;
import com.banco.cuentas.exception.RecursoNoEncontradoException;
import com.banco.cuentas.exception.ServicioNoDisponibleException;
import com.banco.cuentas.repository.CuentaRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.rmi.server.ServerCloneException;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteClient clienteClient;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, ClienteClient clienteClient) {
        this.cuentaRepository = cuentaRepository;
        this.clienteClient = clienteClient;
    }


    @Override
    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta con id " + id + " no encontrada"
                ));
    }

    @Override
    public List<Cuenta> listarPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        try{
            clienteClient.obtenerPorId(cuenta.getClienteId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Cliente con id "+ cuenta.getClienteId()+" no existe.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de clientes no está disponible.");
        }
        boolean cuentaExiste = cuentaRepository.existsByNumeroCuenta(
                cuenta.getNumeroCuenta());
        if (cuentaExiste) {
            throw new RecursoDuplicadoException(
                    "Ya existe una cuenta con el número: " + cuenta.getNumeroCuenta()
            );
        }
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = obtenerPorId(id);
        cuentaRepository.delete(cuenta);
    }
}
