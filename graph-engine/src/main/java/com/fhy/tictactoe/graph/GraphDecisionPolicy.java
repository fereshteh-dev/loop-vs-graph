package com.fhy.tictactoe.graph;

final class GraphDecisionPolicy {
    Decision decide(Strategy strategy, Proposal attack, Proposal defense, Evaluation evaluation) {
        var defensiveBias = strategy == Strategy.DEFENSIVE ? 0.8 : 0.0;
        if (evaluation.defenseScore() + defensiveBias > evaluation.attackScore()) {
            return Decision.from(Intent.DEFENSE, defense, "Defense has the higher risk-adjusted score");
        }
        return Decision.from(Intent.ATTACK, attack, "Attack has the higher risk-adjusted score");
    }
}
