package com.banco.cuentas.service;

import com.banco.cuentas.entity.Cuenta;
import com.banco.cuentas.exception.RecursoDuplicadoException;
import com.banco.cuentas.exception.RecursoNoEncontradoException;
import com.banco.cuentas.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService{

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl (CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
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
        boolean cuentaExiste = cuentaRepository.existsByNumeroCuenta(
                cuenta.getNumeroCuenta());
        if (cuentaExiste) {
            throw new RecursoDuplicadoException(
                    "Ya existe una cuenta con el número: " + cuenta.getNumeroCuenta()
            );
        }
        return  cuentaRepository.save(cuenta);
    }

    @Override
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = obtenerPorId(id);
        cuentaRepository.delete(cuenta);
    }
}
