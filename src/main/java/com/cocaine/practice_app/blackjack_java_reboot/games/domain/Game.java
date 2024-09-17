package com.cocaine.practice_app.blackjack_java_reboot.games.domain;


import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Stack;



@RequiredArgsConstructor
final public class Game {

    private final GameState gameState;

    public void startGame() {

        Stack<Card> deck = gameState.getDeck();

        Collections.shuffle(deck);

        for(Player p : gameState.getPlayers()) {

            Stack<Card> startingHand = new Stack<>();
            startingHand.push(deck.pop());
            startingHand.push(deck.pop());

            p.setStartingHand(startingHand);

        }

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
