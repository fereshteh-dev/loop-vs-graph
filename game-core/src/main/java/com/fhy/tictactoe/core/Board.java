package com.fhy.tictactoe.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public final class Board {
    private static final int SIZE = 3;
    private final Player[] cells;

    private Board(Player[] cells) {
        this.cells = cells;
    }

    public static Board empty() {
        return new Board(new Player[SIZE * SIZE]);
    }

    public Optional<Player> playerAt(Cell cell) {
        Objects.requireNonNull(cell, "cell");
        return Optional.ofNullable(cells[index(cell)]);
    }

    public boolean isEmpty(Cell cell) {
        return playerAt(cell).isEmpty();
    }

    public Board place(Cell cell, Player player) {
        Objects.requireNonNull(cell, "cell");
        Objects.requireNonNull(player, "player");
        if (!isEmpty(cell)) {
            throw new IllegalArgumentException("Cell %s is already occupied".formatted(cell.display()));
        }
        var copy = cells.clone();
        copy[index(cell)] = player;
        return new Board(copy);
    }

    public List<Cell> availableCells() {
        var available = new ArrayList<Cell>();
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                var cell = new Cell(row, column);
                if (isEmpty(cell)) {
                    available.add(cell);
                }
            }
        }
        return List.copyOf(available);
    }

    public int moveCount() {
        return (int) Arrays.stream(cells).filter(Objects::nonNull).count();
    }

    public String compact() {
        var value = new StringBuilder(9);
        for (var player : cells) {
            value.append(player == null ? '-' : player.name());
        }
        return value.toString();
    }

    private static int index(Cell cell) {
        return cell.row() * SIZE + cell.column();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Board board && Arrays.equals(cells, board.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cells);
    }

    @Override
    public String toString() {
        return compact();
    }
}
