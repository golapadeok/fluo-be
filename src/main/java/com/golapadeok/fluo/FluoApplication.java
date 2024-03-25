package com.golapadeok.fluo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FluoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluoApplication.class, args);
    }

}
