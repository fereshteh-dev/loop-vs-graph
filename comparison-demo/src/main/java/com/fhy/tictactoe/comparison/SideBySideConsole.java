package com.fhy.tictactoe.comparison;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

@Component
final class SideBySideConsole {
    private static final int COLUMN_WIDTH = 64;
    private static final String CLEAR_SCREEN = "\u001B[H\u001B[2J";
    private static final String CYAN = "\u001B[96m";
    private static final String MAGENTA = "\u001B[95m";
    private static final String WHITE = "\u001B[97m";
    private static final String DIM = "\u001B[2m";
    private static final String BOLD = "\u001B[1m";
    private static final String RESET = "\u001B[0m";
    private final PrintStream out = System.out;
    private final int delayMillis;
    private final int viewportLines;

    SideBySideConsole(
            @Value("${demo.animation.delay:1600ms}") java.time.Duration delay,
            @Value("${demo.animation.viewport-lines:24}") int viewportLines) {
        this.delayMillis = Math.toIntExact(delay.toMillis());
        this.viewportLines = Math.max(12, viewportLines);
    }

    void render(String loopTranscript, String graphTranscript) throws InterruptedException {
        var left = lines(loopTranscript);
        var right = lines(graphTranscript);
        var height = Math.max(left.size(), right.size());

        for (int visibleLines = 1; visibleLines <= height; visibleLines += 2) {
            drawFrame(left, right, Math.min(visibleLines, height), height);
            Thread.sleep(delayMillis);
        }
        drawFrame(left, right, height, height);
    }

    private void drawFrame(List<String> left, List<String> right, int visibleLines, int totalLines) {
        out.print(CLEAR_SCREEN);
        out.flush();
        out.println();
        out.println(BOLD + WHITE + centered("AI TIC-TAC-TOE • ORCHESTRATION COMPARISON",
                COLUMN_WIDTH * 2 + 5) + RESET);
        out.println(DIM + "═".repeat(COLUMN_WIDTH * 2 + 5) + RESET);
        out.printf("%s%s%s  %s│%s  %s%s%s%n",
                BOLD, CYAN, centered("LOOP ENGINEERING", COLUMN_WIDTH),
                RESET + DIM, RESET, BOLD + MAGENTA,
                centered("GRAPH ENGINEERING", COLUMN_WIDTH), RESET);
        out.printf("%s  %s│%s  %s%n",
                CYAN + centered("GENERALIST • ITERATE • RETRY", COLUMN_WIDTH) + RESET,
                DIM, RESET,
                MAGENTA + centered("SPECIALIZE • HAND OFF • ARBITRATE", COLUMN_WIDTH) + RESET);
        out.println(DIM + "─".repeat(COLUMN_WIDTH) + "──┼──" + "─".repeat(COLUMN_WIDTH) + RESET);

        var firstVisibleLine = Math.max(0, visibleLines - viewportLines);
        for (int index = firstVisibleLine; index < visibleLines; index++) {
            var loopLine = index < left.size() ? left.get(index) : "";
            var graphLine = index < right.size() ? right.get(index) : "";
            out.printf("%s%-" + COLUMN_WIDTH + "s%s  %s│%s  %s%-" + COLUMN_WIDTH + "s%s%n",
                    CYAN, clip(loopLine), RESET, DIM, RESET, MAGENTA, clip(graphLine), RESET);
        }
        for (int index = visibleLines - firstVisibleLine; index < viewportLines; index++) {
            out.printf("%-" + COLUMN_WIDTH + "s  %s│%s  %-" + COLUMN_WIDTH + "s%n",
                    "", DIM, RESET, "");
        }

        out.println(DIM + "─".repeat(COLUMN_WIDTH) + "──┴──" + "─".repeat(COLUMN_WIDTH) + RESET);
        var progress = (int) Math.round(visibleLines * 100.0 / totalLines);
        var footer = visibleLines == totalLines
                ? "COMPLETE • SAME DOMAIN • SAME RULES • DIFFERENT ORCHESTRATION"
                : "RUNNING • %3d%% • WATCH EACH ARCHITECTURE REASON".formatted(progress);
        out.println(BOLD + WHITE + centered(footer, COLUMN_WIDTH * 2 + 5) + RESET);
        out.flush();
    }

    private static List<String> lines(String value) {
        return Arrays.asList(value.replace("\r", "").split("\n", -1));
    }

    private static String clip(String value) {
        if (value.length() <= COLUMN_WIDTH) return value;
        return value.substring(0, COLUMN_WIDTH - 1) + "…";
    }

    private static String centered(String value, int width) {
        var padding = Math.max(0, width - value.length());
        return " ".repeat(padding / 2) + value + " ".repeat(padding - padding / 2);
    }
}
