package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.GameRules;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class LoopConfiguration {
    @Bean GameRules gameRules() { return new GameRules(); }
    @Bean DemoOpponent demoOpponent() { return new DemoOpponent(); }
    @Bean ConsoleView consoleView() { return new ConsoleView(System.out, System.console() != null); }
    @Bean StrategicMoveScorer strategicMoveScorer(GameRules rules) { return new StrategicMoveScorer(rules); }

    @Bean
    @ConditionalOnProperty(name = "demo.ai.enabled", havingValue = "false", matchIfMissing = true)
    MoveReasoner localReasoner(StrategicMoveScorer scorer) {
        return new LocalLoopReasoner(scorer);
    }

    @Bean
    @ConditionalOnProperty(name = "demo.ai.enabled", havingValue = "true")
    MoveReasoner aiReasoner(ChatClient.Builder builder) {
        return new AiLoopReasoner(builder.build());
    }
}
