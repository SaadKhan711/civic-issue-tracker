package com.civictracker.notification_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // We define a RestTemplate bean so Spring can manage it and inject it where needed.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}