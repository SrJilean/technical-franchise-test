package com.technical_franchise_test.technical_franchise_test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Technical Franchise API")
                        .description("Documentación de la API de franquicias con Swagger/OpenAPI")
                        .version("1.0.0"));
    }
}