package com.personal.soshoestore_be;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class SoShoeStoreBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoShoeStoreBeApplication.class, args);
    }
}
