package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;
import org.springframework.stereotype.Component;

import static com.fhy.tictactoe.core.console.Ansi.*;

@Component
final class LoopGameDemo {
    private final GameRules rules;
    private final DemoOpponent opponent;
    private final ConsoleView view;
    private final MoveReasoner reasoner;

    LoopGameDemo(GameRules rules, DemoOpponent opponent, ConsoleView view, MoveReasoner reasoner) {
        this.rules = rules;
        this.opponent = opponent;
        this.view = view;
        this.reasoner = reasoner;
    }

    void run() {
        view.banner("LOOP ENGINEERING", "One agent • one cycle • repeated reasoning", CYAN);
        var game = new GameEngine(rules);
        var loop = new LoopOrchestrator(reasoner, rules, view);
        view.board(game.board());

        while (game.status() instanceof GameStatus.InProgress) {
            view.turn(game.board().moveCount() + 1, game.currentPlayer());
            var move = game.currentPlayer() == Player.X
                    ? opponent.choose(game.board())
                    : loop.choose(game.board(), Player.O);
            if (game.currentPlayer() == Player.X) {
                view.node("Opponent", BLUE, "Scripted move %s (identical in both demos)".formatted(move.display()));
            }
            game.play(move);
            view.board(game.board());
        }
        view.result(game.status());
    }
}
