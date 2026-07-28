package com.fhy.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {
    @Test
    void placesImmutably() {
        var empty = Board.empty();
        var played = empty.place(Cell.at(1, 1), Player.X);
        assertThat(empty.playerAt(Cell.at(1, 1))).isEmpty();
        assertThat(played.playerAt(Cell.at(1, 1))).contains(Player.X);
    }

    @Test
    void rejectsOccupiedCell() {
        var board = Board.empty().place(Cell.at(0, 0), Player.X);
        assertThatIllegalArgumentException().isThrownBy(() -> board.place(Cell.at(0, 0), Player.O));
    }

    @Test
    void exposesAvailableCellsWithoutMutableState() {
        var board = Board.empty().place(Cell.at(0, 0), Player.X);
        assertThat(board.availableCells()).hasSize(8).doesNotContain(Cell.at(0, 0));
    }
}
