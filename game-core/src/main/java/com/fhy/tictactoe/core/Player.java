package com.fhy.tictactoe.core;

public enum Player {
    X, O;

    public Player opponent() {
        return this == X ? O : X;
    }
}
