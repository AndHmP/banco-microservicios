package com.banco.cuentas.service;

import com.banco.cuentas.entity.Cuenta;

import java.util.List;

public interface CuentaService {

    List<Cuenta> listarTodas();

    Cuenta obtenerPorId(Long id);

    List<Cuenta> listarPorCliente(Long clienteId);

    Cuenta crearCuenta(Cuenta cuenta);

    void eliminarCuenta(Long id);
}
