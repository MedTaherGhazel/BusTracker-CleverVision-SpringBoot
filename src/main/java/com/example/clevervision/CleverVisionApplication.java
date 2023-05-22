package com.example.clevervision;

import com.example.clevervision.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling // Enable scheduling

public class CleverVisionApplication {

    public static void main(String[] args) {

        SpringApplication.run(CleverVisionApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(UsersService usersService){
        return args ->{
            usersService.registerAdmin("admin","admin","admin@admin.com");
            usersService.registerUser("user","user","user@user.com");
        };
    }
}

