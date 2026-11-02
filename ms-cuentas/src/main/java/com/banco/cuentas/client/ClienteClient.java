package com.banco.cuentas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", url = "http://localhost:8080")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")

    ClienteDTO obtenerPorId(@PathVariable("id") Long id);
}
