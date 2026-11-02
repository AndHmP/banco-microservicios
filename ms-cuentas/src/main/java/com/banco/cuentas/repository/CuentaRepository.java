package com.banco.cuentas.repository;

import com.banco.cuentas.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByClienteId(Long clienteId);
}
