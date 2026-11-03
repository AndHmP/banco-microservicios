package com.banco.cuentas.service;

import com.banco.cuentas.client.ClienteClient;
import com.banco.cuentas.entity.Cuenta;
import com.banco.cuentas.exception.RecursoDuplicadoException;
import com.banco.cuentas.exception.RecursoNoEncontradoException;
import com.banco.cuentas.exception.ServicioNoDisponibleException;
import com.banco.cuentas.repository.CuentaRepository;
import feign.FeignException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteClient clienteClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CuentaServiceImpl(
            CuentaRepository cuentaRepository,
            ClienteClient clienteClient,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.cuentaRepository = cuentaRepository;
        this.clienteClient = clienteClient;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public List<Cuenta> listarTodas() {

        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta obtenerPorId(
            Long id
    ) {
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
        try {
            clienteClient.obtenerPorId(cuenta.getClienteId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Cliente con id " + cuenta.getClienteId() + " no existe.");
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
        Cuenta cuentaSave = cuentaRepository.save(cuenta);
        kafkaTemplate.send(
                "cuentas-creadas",
                String.valueOf(cuentaSave.getId()),
                "Cuenta " +
                        cuentaSave.getNumeroCuenta() +
                        " creada para el cliente " +
                cuentaSave.getClienteId());
        return cuentaSave;
    }

    @Override
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = obtenerPorId(id);
        cuentaRepository.delete(cuenta);
    }
}
