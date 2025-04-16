package com.zentry.whatsappapi.infrastructure.config;  // ou onde for adequado para a estrutura de seu projeto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a configuração a todos os endpoints em /api/
                .allowedOrigins("http://127.0.0.1:5500") // Permite requisições desta origem
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(true) // Se você precisar lidar com cookies ou informações de autenticação em diferentes origens
                .maxAge(3600); // Tempo (em segundos) que o navegador pode cachear as respostas de preflight
    }
}
