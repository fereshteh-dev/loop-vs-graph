package com.fhy.tictactoe.core;

import java.util.Objects;


public final class GameEngine {
    private final GameRules rules;
    private Board board = Board.empty();
    private Player currentPlayer = Player.X;

    public GameEngine(GameRules rules) {
        this.rules = Objects.requireNonNull(rules);
    }

    public Turn play(Cell cell) {
        if (!rules.isValidMove(board, cell)) {
            throw new IllegalArgumentException("Illegal move %s".formatted(cell.display()));
        }
        var player = currentPlayer;
        board = board.place(cell, player);
        var status = rules.status(board);
        if (status instanceof GameStatus.InProgress) {
            currentPlayer = currentPlayer.opponent();
        }
        return new Turn(board, player, cell, status);
    }

    public Board board() {
        return board;
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    public GameStatus status() {
        return rules.status(board);
    }

    public record Turn(Board board, Player player, Cell cell, GameStatus status) {}
}
