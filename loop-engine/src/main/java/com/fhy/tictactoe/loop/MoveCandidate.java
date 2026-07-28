package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.Cell;

public record MoveCandidate(int row, int column, double score, String rationale) {
    public Cell cell() {
        return new Cell(row - 1, column - 1);
    }
}
