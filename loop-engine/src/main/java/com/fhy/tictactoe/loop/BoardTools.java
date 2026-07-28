package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

final class BoardTools {
    private final Board board;

    BoardTools(Board board) {
        this.board = board;
    }

    @Tool(description = "List all currently legal Tic-Tac-Toe coordinates using one-based row and column")
    String legalMoves() {
        return board.availableCells().stream().map(Cell::display).toList().toString();
    }

    @Tool(description = "Check whether a one-based board coordinate is empty and legal")
    boolean isLegal(
            @ToolParam(description = "Row from 1 to 3") int row,
            @ToolParam(description = "Column from 1 to 3") int column) {
        try {
            return board.isEmpty(new Cell(row - 1, column - 1));
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
