package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.GameRules;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class GraphConfiguration {
    @Bean GameRules gameRules() { return new GameRules(); }
    @Bean DemoOpponent demoOpponent() { return new DemoOpponent(); }
    @Bean ConsoleView consoleView() { return new ConsoleView(System.out, System.console() != null); }
    @Bean TacticalAnalyzer tacticalAnalyzer(GameRules rules) { return new TacticalAnalyzer(rules); }
    @Bean GraphDecisionPolicy graphDecisionPolicy() { return new GraphDecisionPolicy(); }

    @Bean
    @ConditionalOnProperty(name = "demo.ai.enabled", havingValue = "false", matchIfMissing = true)
    AgentIntelligence localIntelligence(TacticalAnalyzer analyzer, GraphDecisionPolicy policy) {
        return new LocalAgentIntelligence(analyzer, policy);
    }

    @Bean
    @ConditionalOnProperty(name = "demo.ai.enabled", havingValue = "true")
    AgentIntelligence aiIntelligence(ChatClient.Builder builder) {
        return new AiAgentIntelligence(builder.build());
    }

    @Bean
    GameGraph gameGraph(AgentIntelligence intelligence, GameRules rules, ConsoleView view) {
        return new GameGraph(new PlannerAgent(intelligence, view),
                new AttackAgent(intelligence, view),
                new DefenseAgent(intelligence, view),
                new EvaluatorAgent(intelligence, view),
                new DecisionAgent(intelligence, view),
                new MoveExecutor(rules, view), view);
    }
}
