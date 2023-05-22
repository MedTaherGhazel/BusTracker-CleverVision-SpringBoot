package com.example.clevervision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // add authorization rules here
                .and()
                .formLogin()
                // add login page configuration here
                .and()
                .logout()
                .logoutUrl("/logout") // set the logout URL
                .logoutSuccessUrl("/login") // set the logout success URL
                .invalidateHttpSession(true) // invalidate HTTP session
                .deleteCookies("JSESSIONID"); // delete cookies
    }



}
