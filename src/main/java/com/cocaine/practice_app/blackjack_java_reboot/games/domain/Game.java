package com.cocaine.practice_app.blackjack_java_reboot.games.domain;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
final public class Game {

    private final Set<Card> deck;
    private final List<Player> players;
    private final Player dealer;
    private final GameState gameState;


    public void startGame() {
        // Initialize and shuffle the deck, deal cards to player and dealer
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
