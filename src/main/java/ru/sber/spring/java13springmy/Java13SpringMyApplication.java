package ru.sber.spring.java13springmy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Java13SpringMyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Java13SpringMyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
