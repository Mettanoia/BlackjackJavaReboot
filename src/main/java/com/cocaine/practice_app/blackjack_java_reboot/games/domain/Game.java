package com.cocaine.practice_app.blackjack_java_reboot.games.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
final public class Game {

    @Getter
    private final Integer id;

    @Getter
    private final GameState gameState;

    public Game(GameState gameState) {
        this(null, gameState);
    }

    public void startGame() {
        if (gameState == null || gameState.getDeck() == null || gameState.getDeck().size() < (gameState.getPlayers().size() * 2) + 2)
            throw new IllegalStateException("Deck must contain cards for all players including the dealer.");

        Deque<Card> deck = (gameState.getDeck());

        shuffleDeck(deck);

        for (Player p : gameState.getPlayers())
            dealStartingHandToPlayer(p, deck);

    }

    /**
     * Deals two cards from the deck to the player.
     * <p>
     * This method <b>MUTATES</b> both, the deck and the player.
     *
     * @param p    a player to deal the two starting cards to
     * @param deck the deck to get the crads from
     */
    private static void dealStartingHandToPlayer(Player p, Deque<Card> deck) {

        Set<Card> startingHand = new HashSet<>(2);

        startingHand.add(deck.pop());

        if (p.getIsDealer()) {
            startingHand.add(deck.pop());
        } else {

            Card card = deck.pop();
            card.setFaceUp(true);
            startingHand.add(deck.pop());
        }

        p.setStartingHand(startingHand);

    }

    /**
     * Shuffles the deck, <b>MUTATING</b> its state.
     *
     * @param deck a Deque to shuffle.
     */
    private static void shuffleDeck(Deque<Card> deck) {

        List<Card> deckAsList = new ArrayList<>(deck);
        Collections.shuffle(deckAsList);

        deck.clear();
        deck.addAll(deckAsList);

    }

    public void dealerTurn() {

        Player dealer = gameState.getPlayers().stream()
                .filter(Player::getIsDealer)
                .toList().get(0); // There is only one dealer

        if (dealer.calculateHandValue() < 17) {

            dealer.hit(gameState.getDeck().pop());

        } else
            dealer.stand();

        finishGame();

    }

    private void finishGame() {

        gameState.getWinners().clear();

        List<Player> dealers = gameState.getPlayers().stream()
                .filter(Player::getIsDealer)
                .toList();

        if (dealers.size() != 1)
            throw new IllegalStateException("There should be exactly one dealer in the game.");

        Player dealer = dealers.get(0);

        handleWinners(dealer);

    }

    private void handleWinners(Player dealer) {

        if (dealer.getPlayerState() == Player.PlayerState.BUSTED) {

            gameState.getPlayers().stream()
                    .filter(player -> player.getPlayerState() != Player.PlayerState.BUSTED)
                    .forEach(player -> gameState.getWinners().add(player));

        } else {

            gameState.getPlayers().stream()
                    .filter(player -> player.calculateHandValue() > dealer.calculateHandValue())
                    .forEach(player -> gameState.getWinners().add(player));

            gameState.getPlayers().stream()
                    .filter(player -> player.calculateHandValue() == dealer.calculateHandValue())
                    .forEach(player -> gameState.getPushedPlayers().add(player));

        }

    }

}
