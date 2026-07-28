package com.fhy.tictactoe.core;


public record Cell(int row, int column) {
    public Cell {
        if (row < 0 || row > 2 || column < 0 || column > 2) {
            throw new IllegalArgumentException("Cell coordinates must be between 0 and 2");
        }
    }

    public static Cell at(int row, int column) {
        return new Cell(row, column);
    }

    public String display() {
        return "(%d,%d)".formatted(row + 1, column + 1);
    }
}
