
package com.wiliam.security.config;

import com.alibaba.fastjson.JSON;
import com.wiliam.security.filter.ValidateCodeFilter;
import com.wiliam.security.handle.AuthenticationFailureHandler;
import com.wiliam.security.properties.SecurityProperties;
import com.wiliam.security.service.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author: liwiliam
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //日志
   // private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;


    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(failureHandler);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/login.html").loginProcessingUrl("/authentication/form")
                .failureHandler(failureHandler)
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                .userDetailsService(service)
                .and()
                .authorizeRequests().antMatchers("/login.html", "/demo/test", "/code/image").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
