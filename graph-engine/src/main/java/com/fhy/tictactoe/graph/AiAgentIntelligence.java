package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Player;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

final class AiAgentIntelligence implements AgentIntelligence {
    private final ChatClient client;

    AiAgentIntelligence(ChatClient client) {
        this.client = client;
    }

    public StrategyResponse plan(Board board, Player player) {
        return call("planner", context(board, player), StrategyResponse.class);
    }
    public Proposal attack(Board board, Player player) {
        return call("attack", context(board, player), Proposal.class);
    }
    public Proposal defend(Board board, Player player) {
        return call("defense", context(board, player), Proposal.class);
    }
    public Evaluation evaluate(Board board, Player player, Proposal attack, Proposal defense) {
        return call("evaluator", context(board, player) + "\nAttack: " + attack + "\nDefense: " + defense,
                Evaluation.class);
    }
    public Decision decide(Strategy strategy, Proposal attack, Proposal defense, Evaluation evaluation) {
        return call("decision", "Strategy: %s%nAttack: %s%nDefense: %s%nEvaluation: %s"
                .formatted(strategy, attack, defense, evaluation), Decision.class);
    }

    private <T> T call(String agent, String input, Class<T> type) {
        return client.prompt()
                .system(prompt("prompts/" + agent + "-agent.st"))
                .user(input)
                .call()
                .entity(type);
    }

    private String context(Board board, Player player) {
        return "Board (row-major, '-' means empty): %s%nYou play: %s".formatted(board.compact(), player);
    }

    private static String prompt(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
