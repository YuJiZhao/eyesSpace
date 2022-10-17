package com.eyes.eyesspace.infrastructure.gateway;

import com.eyes.eyesspace.common.feign.clients.AuthClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = AuthClients.class)
public class GatewayApplication {
    static {
        System.setProperty("archaius.configurationSource.defaultFileName", "application.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}