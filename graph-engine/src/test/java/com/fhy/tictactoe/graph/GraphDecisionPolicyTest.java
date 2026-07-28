package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Cell;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GraphDecisionPolicyTest {
    private final GraphDecisionPolicy policy = new GraphDecisionPolicy();
    private final Proposal attack = Proposal.of(Cell.at(2, 2), 7, "attack");
    private final Proposal defense = Proposal.of(Cell.at(0, 2), 9, "block");

    @Test
    void selectsHigherScoredDefense() {
        var decision = policy.decide(Strategy.BALANCED, attack, defense,
                new Evaluation(7, 9, "mandatory block"));
        assertThat(decision.intent()).isEqualTo(Intent.DEFENSE);
        assertThat(decision.cell()).isEqualTo(Cell.at(0, 2));
    }

    @Test
    void selectsAttackOnTieForOffensiveStrategy() {
        var decision = policy.decide(Strategy.OFFENSIVE, attack, defense,
                new Evaluation(8, 8, "tie"));
        assertThat(decision.intent()).isEqualTo(Intent.ATTACK);
    }
}
