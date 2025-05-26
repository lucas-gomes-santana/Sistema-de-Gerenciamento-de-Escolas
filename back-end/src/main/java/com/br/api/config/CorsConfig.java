package com.br.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    public void mapeamentoPorCors(CorsRegistry corsRegistry) {
        corsRegistry
        .addMapping("/escola/**")
        .allowedOrigins("  http://localhost:5173/")
        .allowedHeaders("*")
        .allowedMethods("PUT", "GET", "POST", "DELETE")
        .allowCredentials(true);
    }
}
