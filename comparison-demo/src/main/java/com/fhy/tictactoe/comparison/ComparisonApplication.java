package com.fhy.tictactoe.comparison;

import com.fhy.tictactoe.graph.GraphTranscript;
import com.fhy.tictactoe.loop.LoopTranscript;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ComparisonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComparisonApplication.class, args);
    }

    @Bean
    CommandLineRunner comparisonRunner(SideBySideConsole console) {
        return ignored -> {
            var loop = capture((output) -> LoopTranscript.writeTo(output, false));
            var graph = capture((output) -> GraphTranscript.writeTo(output, false));
            console.render(loop, graph);
        };
    }

    private static String capture(TranscriptWriter writer) {
        var bytes = new ByteArrayOutputStream();
        try (var output = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            writer.write(output);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    private interface TranscriptWriter {
        void write(PrintStream output);
    }
}
