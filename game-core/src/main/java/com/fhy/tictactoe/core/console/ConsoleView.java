package com.fhy.tictactoe.core.console;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.GameStatus;
import com.fhy.tictactoe.core.Player;

import java.io.PrintStream;

import static com.fhy.tictactoe.core.console.Ansi.*;

public final class ConsoleView {
    private final PrintStream out;
    private final boolean color;

    public ConsoleView(PrintStream out, boolean color) {
        this.out = out;
        this.color = color;
    }

    public void banner(String title, String subtitle, String accent) {
        out.println();
        out.println(c(accent, "╔══════════════════════════════════════════════════════════════╗"));
        out.printf(c(accent, "║") + "  " + c(BOLD + WHITE, "%-58s") + c(accent, "║") + "%n", title);
        out.printf(c(accent, "║") + "  " + c(DIM + WHITE, "%-58s") + c(accent, "║") + "%n", subtitle);
        out.println(c(accent, "╚══════════════════════════════════════════════════════════════╝"));
    }

    public void turn(int turn, Player player) {
        out.printf("%n%s  %s%n", c(BOLD + CYAN, "TURN %02d".formatted(turn)),
                c(DIM + WHITE, "• Player " + player));
    }

    public void board(Board board) {
        out.println(c(DIM + WHITE, "       1       2       3"));
        for (int row = 0; row < 3; row++) {
            if (row > 0) out.println(c(DIM + WHITE, "   ───────┼───────┼───────"));
            out.printf(c(DIM + WHITE, " %d  ".formatted(row + 1)));
            for (int column = 0; column < 3; column++) {
                var value = board.playerAt(new Cell(row, column))
                        .map(this::player)
                        .orElse(c(DIM + WHITE, "·"));
                out.printf("  %s  %s", value, column < 2 ? c(DIM + WHITE, "│") : "");
            }
            out.println();
        }
        out.println();
    }

    public void node(String name, String color, String message) {
        out.printf("  %s %s%n", c(color, "◆ %-11s".formatted(name)), message);
    }

    public void edge() {
        out.println(c(DIM + WHITE, "       │"));
        out.println(c(DIM + WHITE, "       ▼"));
    }

    public void result(GameStatus status) {
        var message = switch (status) {
            case GameStatus.Won won -> "PLAYER %s WINS".formatted(won.winner());
            case GameStatus.Draw ignored -> "DRAW — PERFECTLY BALANCED";
            case GameStatus.InProgress ignored -> "GAME IN PROGRESS";
        };
        out.println(c(GREEN + BOLD, "  ╭──────────────────────────────────────────────╮"));
        out.printf(c(GREEN + BOLD, "  │  %-44s│%n"), message);
        out.println(c(GREEN + BOLD, "  ╰──────────────────────────────────────────────╯"));
    }

    public void line(String text) {
        out.println(text);
    }

    private String player(Player player) {
        return c(BOLD + (player == Player.X ? BLUE : MAGENTA), player.name());
    }

    private String c(String style, String value) {
        return color ? style + value + RESET : value;
    }
}
