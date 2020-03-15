package com.wiliam.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author:liwiliam
 */
@SpringBootApplication
public class ApplicationRunnerStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunnerStarter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
