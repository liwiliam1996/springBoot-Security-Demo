package com.wiliam.security;

import com.wiliam.security.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author:liwiliam
 */
@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})
public class ApplicationRunnerStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunnerStarter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
