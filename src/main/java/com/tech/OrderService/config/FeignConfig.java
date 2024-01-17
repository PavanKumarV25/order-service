package com.tech.OrderService.config;

import com.tech.OrderService.exception.CustomException;
import com.tech.OrderService.exception.external.decoder.CustomeErrorDecoder;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomeErrorDecoder();
    }


}
