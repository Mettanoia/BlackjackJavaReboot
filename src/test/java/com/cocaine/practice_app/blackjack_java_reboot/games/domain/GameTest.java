package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    private GameState gameState;
    private Game game;

    @BeforeEach
    void setUp() {

        gameState = mock(GameState.class);

        game = new Game(gameState);

    }

    @ParameterizedTest
    @MethodSource("provideStartGameData")
    void testStartGame(Deque<Card> deck, ArrayList<Player> players, boolean shouldThrowException) {

        when(gameState.getPlayers()).thenReturn(new ArrayList<>());
        when(gameState.getDeck()).thenReturn(new ArrayDeque<>());

        gameState.getPlayers().addAll(players);
        gameState.getDeck().addAll(deck);

        if (shouldThrowException) {
            assertThrows(IllegalStateException.class, game::startGame);
        } else {

            game.startGame();

            for (Player player : players)
                assertEquals(2, player.getHand().size());

        }

    }

    @Test
    void testDealerTurnWhenDealerHits() {

        // Mock the GameState and Deck
        Deque<Card> deck = new ArrayDeque<>();
        Card nextCard = new Card(Card.Rank.FIVE, Card.Suit.CLUBS); // Card for dealer to hit
        deck.add(nextCard);
        when(gameState.getDeck()).thenReturn(deck);

        // Mock the dealer (Player)
        Player dealer = mock(Player.class);
        when(dealer.getIsDealer()).thenReturn(true);
        when(dealer.calculateHandValue()).thenReturn(16); // First call: hand value 16
        when(gameState.getPlayers()).thenReturn(new ArrayList<>(List.of(dealer)));

        // Call dealerTurn()
        game.dealerTurn();

        // Verify that the dealer hits because hand value is less than 17
        verify(dealer, times(1)).hit(nextCard);
        verify(dealer, never()).stand();
    }

    @Test
    void testDealerTurnWhenDealerStands() {

        Deque<Card> deck = new ArrayDeque<>();
        when(gameState.getDeck()).thenReturn(deck); // Dealer won't hit, so no need for a card

        // Mock the dealer (Player)
        Player dealer = mock(Player.class);
        when(dealer.getIsDealer()).thenReturn(true);
        when(dealer.calculateHandValue()).thenReturn(17); // Second call: hand value 17
        when(gameState.getPlayers()).thenReturn(new ArrayList<>(List.of(dealer)));

        // Call dealerTurn()
        game.dealerTurn();

        // Verify that the dealer stands because hand value is 17
        verify(dealer, never()).hit(any(Card.class)); // Dealer should not hit
        verify(dealer, times(1)).stand();

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

        players.add(new Player("Dealer", "dealer@777.es", true));

        return players;

    }

}
