package com.eyes.eyesspace.service.article;

import com.eyes.eyesspace.common.feign.clients.SiteClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.eyes.eyesspace.service.article",
        "com.eyes.eyesspace.common.service"
})
@EnableFeignClients(clients = {SiteClients.class})
public class ServiceArticleApplication {
    static {
        System.setProperty("archaius.configurationSource.defaultFileName", "application.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleApplication.class, args);
    }
}
