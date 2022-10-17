package com.eyes.eyesspace.service.backstage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.eyes.eyesspace.service.backstage",
        "com.eyes.eyesspace.common.service"
})
@EnableFeignClients
public class ServiceBackstageApplication {
    static {
        System.setProperty("archaius.configurationSource.defaultFileName", "application.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceBackstageApplication.class, args);
    }
}
