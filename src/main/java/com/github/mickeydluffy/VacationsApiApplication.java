package com.github.mickeydluffy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class VacationsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacationsApiApplication.class, args);
    }

}
