package com.banco.clienteapi.controller;

import com.banco.clienteapi.dto.ClienteRequest;
import com.banco.clienteapi.dto.ClienteResponse;
import com.banco.clienteapi.entity.Cliente;
import com.banco.clienteapi.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar(){
        return clienteService.listarTodos().stream()
                .map(ClienteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ClienteResponse obtenerPorId(@PathVariable Long id){
        return ClienteResponse.fromEntity(clienteService.obtenerPorId(id));
    }


    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = request.toEntity();
        ClienteResponse nuevo = ClienteResponse.fromEntity(clienteService.crearCliente(cliente));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request){
        Cliente cliente = request.toEntity();
        return ClienteResponse.fromEntity(clienteService.actualizarCliente(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
