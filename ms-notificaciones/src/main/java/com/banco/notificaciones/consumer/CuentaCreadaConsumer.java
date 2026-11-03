package com.banco.notificaciones.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CuentaCreadaConsumer {

    @KafkaListener(topics = "cuentas-creadas")
    public void escuchar(String mensaje){
        System.out.println("Enviando correo de bienvenida: "+ mensaje);
    }
}
