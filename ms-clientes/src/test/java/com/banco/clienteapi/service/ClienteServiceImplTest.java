package com.banco.clienteapi.service;

import com.banco.clienteapi.entity.Cliente;
import com.banco.clienteapi.exception.RecursoDuplicadoException;
import com.banco.clienteapi.exception.RecursoNoEncontradoException;
import com.banco.clienteapi.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void crearCliente_cuandoDniYEmailNoExiste_guardarElCliente() {

        // Given
        Cliente cliente = new Cliente(null, "Ana", "Torres", "12345678", "ana@correo.com");
        Cliente clienteGuardado = new Cliente(1L, "Ana", "Torres", "12345678", "ana@correo.com");

        Mockito.when(clienteRepository.existsByDni("12345678")).thenReturn(false);
        Mockito.when(clienteRepository.existsByEmail("ana@correo.com")).thenReturn(false);
        Mockito.when(clienteRepository.save(cliente)).thenReturn(clienteGuardado);

        // When
        Cliente resultado = clienteService.crearCliente(cliente);

        // Then
        Assertions.assertThat(resultado.getId()).isEqualTo(1L);
        Mockito.verify(clienteRepository).save(cliente);
    }

    @Test
    void crearCliente_cuandoDniExiste_lanzarExcepcion() {

        // Given
        Cliente cliente = new Cliente(null, "Juan", "Luna", "12345678", "aluna@gmail.com");
        Mockito.when(clienteRepository.existsByDni("12345678")).thenReturn(true);

        // When + Then
        Assertions.assertThatThrownBy(() -> clienteService.crearCliente(cliente))
                .isInstanceOf(RecursoDuplicadoException.class);
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzarExcepcion() {

        // Given
        Mockito.when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        Assertions.assertThatThrownBy(() -> clienteService.obtenerPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
        Mockito.verify(clienteRepository).findById(99L);
    }
}
