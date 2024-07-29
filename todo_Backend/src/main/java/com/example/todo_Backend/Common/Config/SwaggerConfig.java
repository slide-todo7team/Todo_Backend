package com.example.todo_Backend.Common.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@OpenAPIDefinition(
        servers = {
                @Server(url="http://52.78.126.130:8080/", description = "Default Server url")
        }
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi pageApi() {
        String[] paths = {
                "/api/member/**",
                "/api/groups/**",
                "/api/auth/**",
                "/api/**"
        };

        return GroupedOpenApi.builder()
                .group("Todo")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        List<Tag> tagList = List.of(
                createTag("회원 API", "회원 관련 API")
        );

        String key = "Access Token (Bearer)";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(key);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme);

        return new OpenAPI()
                .info(
                new Info().title("Slid-Todo")
                        .description("Slid-Todo Swagger 페이지")
                        .version("v1"))
                .tags(tagList)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Tag createTag(String name, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }

}
