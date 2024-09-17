package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private GameState gameState;
    private Game game;

    @BeforeEach
    void setUp() {
        gameState = new GameState(new ArrayDeque<>(), new ArrayList<>());
        game = new Game(gameState);
    }

    @ParameterizedTest
    @MethodSource("provideStartGameData")
    void testStartGame(Deque<Card> deck, ArrayList<Player> players, boolean shouldThrowException) {

        gameState.getPlayers().addAll(players);
        gameState.getDeck().addAll(deck);

        if (shouldThrowException) {
            assertThrows(IllegalStateException.class, game::startGame);
        } else {

            game.startGame();

            // Check if the dealer was properly created
            assertEquals(players.size() + 1, gameState.getPlayers().size());

            for (Player player : players)
                assertEquals(2, player.getHand().size());

        }

    }

    static Stream<Arguments> provideStartGameData() {

        return Stream.of(
                Arguments.of(generateDeckWithNCards(8), generatePlayers(2), false),
                Arguments.of(generateDeckWithNCards(4), generatePlayers(2), true),
                Arguments.of(generateDeckWithNCards(12), generatePlayers(3), false)
        );

    }

    static Deque<Card> generateDeckWithNCards(int n) {

        Deque<Card> deck = new ArrayDeque<>();

        for (int i = 0; i < n; i++)
            deck.add(new Card(Card.Rank.TWO, Card.Suit.CLUBS));

        return deck;

    }

    static List<Player> generatePlayers(int numPlayers) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {

            String playerName = "Player" + (i + 1);
            String playerEmail = "player" + (i + 1) + "@example.com";

            players.add(new Player(playerName, playerEmail));

        }

        return players;

    }

}
