package com.golapadeok.fluo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@OpenAPIDefinition(
        servers = {
                @Server(url="https://project-application.shop:443", description = "Default Server url")
        }
)
@ConfigurationPropertiesScan
@SpringBootApplication
public class FluoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluoApplication.class, args);
    }

}
