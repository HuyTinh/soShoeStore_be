package com.personal.soshoestore_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SoShoeStoreBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoShoeStoreBeApplication.class, args);
    }
}
