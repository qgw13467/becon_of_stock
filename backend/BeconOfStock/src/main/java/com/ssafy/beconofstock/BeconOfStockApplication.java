package com.ssafy.beconofstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeconOfStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeconOfStockApplication.class, args);
    }

}
