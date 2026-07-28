package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;
import org.springframework.stereotype.Component;

import static com.fhy.tictactoe.core.console.Ansi.*;

@Component
final class GraphGameDemo {
    private final GameRules rules;
    private final DemoOpponent opponent;
    private final ConsoleView view;
    private final GameGraph graph;

    GraphGameDemo(GameRules rules, DemoOpponent opponent, ConsoleView view, GameGraph graph) {
        this.rules = rules; this.opponent = opponent; this.view = view; this.graph = graph;
    }

    void run() {
        view.banner("GRAPH ENGINEERING", "Specialist agents • explicit edges • shared state", MAGENTA);
        var game = new GameEngine(rules);
        view.board(game.board());
        while (game.status() instanceof GameStatus.InProgress) {
            view.turn(game.board().moveCount() + 1, game.currentPlayer());
            var move = game.currentPlayer() == Player.X
                    ? opponent.choose(game.board())
                    : graph.execute(game.board(), Player.O);
            if (game.currentPlayer() == Player.X) {
                view.node("Opponent", BLUE, "Scripted move %s (identical in both demos)".formatted(move.display()));
            }
            game.play(move);
            view.board(game.board());
        }
        view.result(game.status());
    }
}
