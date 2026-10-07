package com.farkhod.famousbooksapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FamousBooksAppApplication {

    static void main(String[] args) {
        SpringApplication.run(FamousBooksAppApplication.class, args);
    }

}
