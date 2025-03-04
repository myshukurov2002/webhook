package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public CommandLineRunner initBot(ApplicationContext applicationContext) {
        return args -> {
            Register register = applicationContext.getBean(Register.class);
            register
                    .registerWebhook();
        };
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
