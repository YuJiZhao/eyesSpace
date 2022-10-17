package com.eyes.eyesspace.service.entertain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.eyes.eyesspace.service.entertain",
        "com.eyes.eyesspace.common.service"
})
public class ServiceEntertainApplication {
    static {
        System.setProperty("archaius.configurationSource.defaultFileName", "application.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceEntertainApplication.class, args);
    }
}
