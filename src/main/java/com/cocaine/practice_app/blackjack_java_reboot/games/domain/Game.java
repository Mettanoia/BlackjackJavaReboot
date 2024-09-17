package com.cocaine.practice_app.blackjack_java_reboot.games.domain;


import lombok.RequiredArgsConstructor;

import java.util.*;


@RequiredArgsConstructor
final public class Game {

    private final GameState gameState;

    public void startGame() {
        if (gameState == null || gameState.getDeck() == null || gameState.getDeck().size() < (gameState.getPlayers().size() * 2) + 2)
            throw new IllegalStateException("Deck must contain cards for all players including the dealer.");

        Deque<Card> deck = (gameState.getDeck());

        shuffleDeck(deck);

        for(Player p : gameState.getPlayers())
            dealStartingHandToPlayer(p, deck);

        // Dealer player and its starting hand

        Player dealer = new Player("Dealer", "dealer@777.com", true);

        dealStartingHandToPlayer(dealer, deck);

        gameState.getPlayers().add(dealer);

    }

    /**
     * Deals two cards from the deck to the player.
     *
     * This method <b>MUTATES</b> both, the deck and the player.
     *
     * @param p a player to deal the two starting cards to
     * @param deck the deck to get the crads from
     */
    private static void dealStartingHandToPlayer(Player p, Deque<Card> deck) {

        Set<Card> startingHand = new HashSet<>(2);
        startingHand.add(deck.pop());
        startingHand.add(deck.pop());

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

    public void playerHit() {
        // Deal a card to the player, check if the player busts or has blackjack
    }

    public void playerStand() {
        // Transition to the dealer's turn
    }

    public void dealerTurn() {
        // Dealer hits if hand is less than 17, stands otherwise
    }

    public boolean isPlayerBusted() {
        // Check if player's hand value exceeds 21
        return false;
    }

    public boolean isDealerBusted() {
        // Check if dealer's hand value exceeds 21
        return false;
    }

}
