package com.eyes.eyesblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableWebMvc
@EnableOpenApi
public class EyesblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyesblogApplication.class, args);
    }

}
