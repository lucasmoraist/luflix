package com.lucasmoraist.luflix.infrastructure.config.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Luflix API")
                        .version("1.0.0")
                        .description("API para gerenciamento de vídeos e categorias da plataforma Luflix")
                        .contact(new Contact()
                                .name("lucasmoraist")
                                .url("https://github.com/lucasmoraist")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT. Exemplo: eyJhbGciOiJIUzI1NiIs...")
                        )
                );
    }

}
