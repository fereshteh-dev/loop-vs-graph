package com.fhy.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameEngineTest {
    @Test
    void alternatesTurnsAndStopsAfterWin() {
        var engine = new GameEngine(new GameRules());
        engine.play(Cell.at(0, 0));
        engine.play(Cell.at(1, 0));
        engine.play(Cell.at(0, 1));
        engine.play(Cell.at(1, 1));
        var winningTurn = engine.play(Cell.at(0, 2));

        assertThat(winningTurn.status()).isEqualTo(new GameStatus.Won(Player.X));
        assertThatThrownBy(() -> engine.play(Cell.at(2, 2))).isInstanceOf(IllegalArgumentException.class);
    }
}
