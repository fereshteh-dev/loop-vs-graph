package com.fhy.tictactoe.core;

public sealed interface GameStatus permits GameStatus.InProgress, GameStatus.Won, GameStatus.Draw {
    record InProgress() implements GameStatus {}
    record Won(Player winner) implements GameStatus {}
    record Draw() implements GameStatus {}
}
