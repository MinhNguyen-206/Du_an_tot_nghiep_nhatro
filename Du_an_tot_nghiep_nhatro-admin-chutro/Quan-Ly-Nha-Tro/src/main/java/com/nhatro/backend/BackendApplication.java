package com.nhatro.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Bat buoc phai co khi packaging=war de co the deploy len 1 Tomcat ngoai
    // (khong bat buoc khi chi chay bang "mvn spring-boot:run" o may dev,
    // nhung nen giu de tien mo rong sau nay).
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BackendApplication.class);
    }
}
