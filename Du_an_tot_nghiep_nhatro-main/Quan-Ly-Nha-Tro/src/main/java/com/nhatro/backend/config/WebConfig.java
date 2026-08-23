package com.nhatro.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cấu hình tài nguyên tĩnh nằm trong src/main/webapp/resources.
 *
 * URL trình duyệt:
 *   /resources/css/admin.css
 *
 * Đường dẫn project:
 *   src/main/webapp/resources/css/admin.css
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
