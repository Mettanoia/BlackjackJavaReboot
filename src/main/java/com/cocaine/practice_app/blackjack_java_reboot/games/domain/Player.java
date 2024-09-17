package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
@AllArgsConstructor
final class Player {

    private final String name;
    private final String email;

    @Setter(AccessLevel.PRIVATE)
    private PlayerState playerState;

    @Setter(AccessLevel.PRIVATE)
    private Collection<Card> hand;

    Player(String name, String email) {

        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.hand = new ArrayList<>();

    }

    void setStartingHand(Collection<Card> startingHand) {
        if (startingHand == null || startingHand.size() != 2)
            throw new IllegalArgumentException("startingHand must have exactly two cards.");

        setHand(new ArrayList<>(startingHand));

        setPlayerState(hasBlackjack() ? PlayerState.BLACKJACK : PlayerState.PLAYING);

    }

    void hit(Card card) {

        hand.add(Objects.requireNonNull(card));

        if (isBusted())
            setPlayerState(PlayerState.BUSTED);

    }

    Boolean isBusted() {
        return calculateHandValue() > 21;
    }

    Boolean hasBlackjack() {
        return calculateHandValue() == 21;
    }

    int calculateHandValue() {

        int totalValue = hand.stream()
                .mapToInt(card -> card.getRank().getValue())
                .sum();

        long aceCount = hand.stream()
                .filter(card -> card.getRank() == Card.Rank.ACE)
                .count();

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;

    }

    void stand() {
        setPlayerState(PlayerState.STANDING);
    }

    enum PlayerState {
        PLAYING, BUSTED, BLACKJACK, STANDING
    }

}
