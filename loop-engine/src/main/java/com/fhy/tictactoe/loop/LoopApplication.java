package com.fhy.tictactoe.loop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoopApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoopApplication.class, args);
    }

    @Bean
    CommandLineRunner demoRunner(LoopGameDemo demo) {
        return args -> demo.run();
    }
}
