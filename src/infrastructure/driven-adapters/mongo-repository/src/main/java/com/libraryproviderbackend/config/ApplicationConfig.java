package com.libraryproviderbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Infrastructure-level Spring configuration for the mongo-repository module.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Provides a shared {@link ObjectMapper} with Java 8+ time support registered.
     * Spring Boot auto-configuration will pick this up for HTTP serialization as well.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }
}
