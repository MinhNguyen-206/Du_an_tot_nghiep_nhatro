package com.nhatro.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nhaTroOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nha Tro API Documentation")
                        .description("Tai lieu va thu nghiem truc tiep cac API cua he thong quan ly thue nha tro")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nhom du an tot nghiep Nha Tro")));
    }
}