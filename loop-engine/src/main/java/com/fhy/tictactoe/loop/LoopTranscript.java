package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;

import java.io.PrintStream;

import static com.fhy.tictactoe.core.console.Ansi.*;


public final class LoopTranscript {
    private LoopTranscript() {}

    public static void writeTo(PrintStream output, boolean color) {
        var rules = new GameRules();
        var view = new ConsoleView(output, color);
        var reasoner = new LocalLoopReasoner(new StrategicMoveScorer(rules));
        var opponent = new DemoOpponent();
        var game = new GameEngine(rules);
        var loop = new LoopOrchestrator(reasoner, rules, view);

        view.banner("LOOP ENGINEERING", "One agent • bounded reasoning cycle", CYAN);
        view.board(game.board());
        while (game.status() instanceof GameStatus.InProgress) {
            view.turn(game.board().moveCount() + 1, game.currentPlayer());
            var move = game.currentPlayer() == Player.X
                    ? opponent.choose(game.board())
                    : loop.choose(game.board(), Player.O);
            if (game.currentPlayer() == Player.X) {
                view.node("Opponent", BLUE, "Fixed input → %s".formatted(move.display()));
            }
            game.play(move);
            view.board(game.board());
        }
        view.result(game.status());
    }
}
