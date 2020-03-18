package com.wiliam.security;

import com.alibaba.fastjson.JSON;
import com.wiliam.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author:liwiliam
 */
@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})
public class ApplicationRunnerStarter {

    private static Logger logger = LoggerFactory.getLogger(ApplicationRunnerStarter.class);


    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunnerStarter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
