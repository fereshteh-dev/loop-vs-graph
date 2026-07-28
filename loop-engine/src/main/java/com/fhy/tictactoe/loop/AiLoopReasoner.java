package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.Player;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

final class AiLoopReasoner implements MoveReasoner {
    private final ChatClient chatClient;
    private final String systemPrompt;

    AiLoopReasoner(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.systemPrompt = prompt("prompts/loop-system.st");
    }

    @Override
    public MoveCandidate propose(Board board, Player player, Set<Cell> rejected) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(spec -> spec.text("""
                                Board: {board}
                                You play: {player}
                                Previously rejected moves: {rejected}
                                Inspect the board with tools, then return one candidate.
                                """)
                        .param("board", board.compact())
                        .param("player", player.name())
                        .param("rejected", rejected.toString()))
                .tools(new BoardTools(board))
                .call()
                .entity(MoveCandidate.class);
    }

    private static String prompt(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
