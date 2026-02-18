package com.Arthur.API_Cliente.config;

import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${feign.retryer.period}")
    private Integer period;
    @Value("${feign.retryer.maxPeriod}")
    private Integer maxPeriod;
    @Value("${feign.retryer.maxAttempts}")
    private Integer maxAttempts;

    @Bean
    public Retryer retryer() {

        return new Retryer.Default(period, maxPeriod, maxAttempts);
    }
}