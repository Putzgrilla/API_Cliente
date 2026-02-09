package com.Arthur.API_Cliente.Config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        // Tenta 3 vezes, esperando 1 segundo entre cada tentativa
        return new Retryer.Default(1000, 2000, 3);
    }
}