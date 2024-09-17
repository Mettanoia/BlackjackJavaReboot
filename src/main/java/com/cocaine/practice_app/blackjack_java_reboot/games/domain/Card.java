package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
final class Card {

    private final Rank rank;
    private final Suit suit;
    private final Boolean faceUp;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.faceUp = false;
    }

    @RequiredArgsConstructor
    public enum Rank {

        TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(1, 11);

        private final int lowValue;
        private final int highValue;

        Rank(int value) {
            this.lowValue = value;
            this.highValue = value;
        }

        public int getValue() {
            return highValue;
        }

        public int getValue(Boolean high) {
            return this.ordinal() == 12 ?
                    high ? highValue : lowValue : highValue; // highValue unless it is an Ace (ordinal 12) and high is true
        }

    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

}
