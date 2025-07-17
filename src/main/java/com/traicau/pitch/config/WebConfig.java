package com.traicau.pitch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${path.image.folder}")
    private String folderDirection;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ánh xạ yêu cầu /uploads/** đến thư mục D:/uploads/
        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("classpath:/uploads/");

//                .addResourceLocations("file:/root/uploads/");
//                .addResourceLocations("file:/D:/workspace/intelliji-workspace/pitch/uploads/");
        .addResourceLocations("file:/"+folderDirection);
    }
}