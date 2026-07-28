package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StrategicMoveScorerTest {
    private final StrategicMoveScorer scorer = new StrategicMoveScorer(new GameRules());

    @Test
    void ranksImmediateWinFirst() {
        var board = Board.empty()
                .place(Cell.at(0, 0), Player.O).place(Cell.at(0, 1), Player.O)
                .place(Cell.at(1, 0), Player.X).place(Cell.at(1, 1), Player.X);
        assertThat(scorer.rank(board, Player.O).getFirst().cell()).isEqualTo(Cell.at(0, 2));
    }

    @Test
    void blocksForcedLoss() {
        var board = Board.empty()
                .place(Cell.at(0, 0), Player.X).place(Cell.at(0, 1), Player.X)
                .place(Cell.at(1, 1), Player.O);
        assertThat(scorer.rank(board, Player.O).getFirst().cell()).isEqualTo(Cell.at(0, 2));
    }
}
