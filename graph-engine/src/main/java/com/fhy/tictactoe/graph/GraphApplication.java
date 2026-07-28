package com.fhy.tictactoe.graph;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphApplication.class, args);
    }

    @Bean
    CommandLineRunner demoRunner(GraphGameDemo demo) {
        return args -> demo.run();
    }
}
