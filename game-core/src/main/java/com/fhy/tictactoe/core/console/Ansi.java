package com.fhy.tictactoe.core.console;

public final class Ansi {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[94m";
    public static final String MAGENTA = "\u001B[95m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String RED = "\u001B[91m";
    public static final String WHITE = "\u001B[97m";

    private Ansi() {}

    public static String paint(String color, String text) {
        return color + text + RESET;
    }
}
