package com.fhy.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameRulesTest {
    private final GameRules rules = new GameRules();

    @Test
    void detectsEveryWinningDirection() {
        for (var line : rules.winningLines()) {
            var board = Board.empty();
            for (var cell : line) board = board.place(cell, Player.O);
            assertThat(rules.status(board)).isEqualTo(new GameStatus.Won(Player.O));
        }
    }

    @Test
    void detectsDraw() {
        var board = board("XOXXOOOXX");
        assertThat(rules.status(board)).isInstanceOf(GameStatus.Draw.class);
    }

    @Test
    void validatesOnlyEmptyCellsWhileGameIsActive() {
        var board = Board.empty().place(Cell.at(0, 0), Player.X);
        assertThat(rules.isValidMove(board, Cell.at(0, 0))).isFalse();
        assertThat(rules.isValidMove(board, Cell.at(1, 1))).isTrue();
    }

    private Board board(String cells) {
        var board = Board.empty();
        for (int index = 0; index < cells.length(); index++) {
            if (cells.charAt(index) != '-') {
                board = board.place(Cell.at(index / 3, index % 3), Player.valueOf("" + cells.charAt(index)));
            }
        }
        return board;
    }
}
