package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Player;

final class LocalAgentIntelligence implements AgentIntelligence {
    private final TacticalAnalyzer analyzer;
    private final GraphDecisionPolicy policy;

    LocalAgentIntelligence(TacticalAnalyzer analyzer, GraphDecisionPolicy policy) {
        this.analyzer = analyzer;
        this.policy = policy;
    }

    public StrategyResponse plan(Board board, Player player) {
        var strategy = board.moveCount() < 4 ? Strategy.OFFENSIVE : Strategy.BALANCED;
        return new StrategyResponse(strategy, "Adapted to board phase and available initiative");
    }
    public Proposal attack(Board board, Player player) { return analyzer.attack(board, player); }
    public Proposal defend(Board board, Player player) { return analyzer.defend(board, player); }
    public Evaluation evaluate(Board board, Player player, Proposal attack, Proposal defense) {
        return new Evaluation(attack.confidence(), defense.confidence(),
                attack.cell().equals(defense.cell()) ? "Agents converge on the same move" : "Trade-off requires arbitration");
    }
    public Decision decide(Strategy strategy, Proposal attack, Proposal defense, Evaluation evaluation) {
        return policy.decide(strategy, attack, defense, evaluation);
    }
}
