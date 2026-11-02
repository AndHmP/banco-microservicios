package com.banco.cuentas.controller;

import com.banco.cuentas.dto.CuentaRequest;
import com.banco.cuentas.dto.CuentaResponse;
import com.banco.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<CuentaResponse> listar() {
        return cuentaService.listarTodas().stream()
                .map(CuentaResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public CuentaResponse obtenerCuentaPorId(@PathVariable Long id) {
        return CuentaResponse.fromEntity(cuentaService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CuentaResponse> obtenerCuentasPorClienteId(@PathVariable Long clienteId) {
        return cuentaService.listarPorCliente(clienteId).stream()
                .map(CuentaResponse::fromEntity).toList();
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crearCuenta(@Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CuentaResponse.fromEntity(cuentaService.crearCuenta(request.toEntity()))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
