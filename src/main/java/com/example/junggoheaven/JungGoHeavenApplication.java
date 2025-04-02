package com.example.junggoheaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JungGoHeavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JungGoHeavenApplication.class, args);
    }

}
