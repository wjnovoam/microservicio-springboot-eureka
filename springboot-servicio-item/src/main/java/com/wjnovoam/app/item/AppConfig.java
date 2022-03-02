package com.wjnovoam.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("clienteRest")
    @LoadBalanced //Va a utilizar Ribben para el balanceo de carga
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }

}
