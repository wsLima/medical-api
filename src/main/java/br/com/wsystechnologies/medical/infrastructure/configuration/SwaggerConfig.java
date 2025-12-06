package br.com.wsystechnologies.medical.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement())
                .components(components());
    }

    private Info apiInfo() {
        return new Info()
                .title("Medical API")
                .version("v1")
                .description("Plataforma de Gestão Medica");
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(SECURITY_SCHEME);
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME, securityScheme())
                .addResponses("NotFound", new ApiResponse().description("Resource not found"))
                .addResponses("BadRequest", new ApiResponse().description("Invalid request"))
                .addResponses("Unauthorized", new ApiResponse().description("Unauthorized"))
                .addResponses("Forbidden", new ApiResponse().description("Forbidden"))
                .addResponses("InternalError", new ApiResponse().description("Internal server error"));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}