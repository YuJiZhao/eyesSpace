package com.eyes.eyesspace.service.site;

import com.eyes.eyesspace.common.feign.clients.ArticleClients;
import com.eyes.eyesspace.common.feign.clients.SiteClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.eyes.eyesspace.service.site",
        "com.eyes.eyesspace.common.service"
})
@EnableFeignClients(clients = {ArticleClients.class, SiteClients.class})
public class ServiceSiteApplication {
    static {
        System.setProperty("archaius.configurationSource.defaultFileName", "application.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceSiteApplication.class, args);
    }
}