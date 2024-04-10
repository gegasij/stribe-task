package com.example.spribetask.openexchangerates.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {
    @Value("${openexchangerates.apiKey}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return template -> template.query("app_id", apiKey);
    }
}
