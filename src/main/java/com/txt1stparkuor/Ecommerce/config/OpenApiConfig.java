package com.txt1stparkuor.Ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final String applicationName = "HIT API Ecommerce Backend";

    private final String SECURITY_SCHEME_NAME = "BEARER Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(info())
                .servers(servers())
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }

    private Info info() {
        return new Info()
                .title(applicationName)
                .version("1.0.0")
                .description("API documentation for HIT backend")
                .license(license())
                .contact(contact());
    }

    private Contact contact() {
        return new Contact()
                .name("HIT Team")
                .email("txt1stparkuor@gmail.com");
    }

    private License license() {
        return new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
    }

    private List<Server> servers() {
        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("Development Server");

        Server prodServer = new Server()
                .url("null")
                .description("Production Server");

        return List.of(devServer, prodServer);
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter JWT token obtained from login endpoint");
    }
}
