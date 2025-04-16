package com.VenTrix.com.VenTrix.Configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:/app/imagenes/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir solicitudes a todas las rutas
<<<<<<< HEAD
                .allowedOrigins("http://localhost:5173","http://localhost:8081") // Permitir solicitudes desde el frontend en localhost
=======
                .allowedOrigins("http://localhost:5173", "http://localhost:8081") // Permitir solicitudes desde el frontend en localhost
>>>>>>> f6a82c44d3e060f654ac120042ceb3c948199a23
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Permitir todos los encabezados
                .allowCredentials(true); // Permitir el envío de cookies si es necesario
    }
}
