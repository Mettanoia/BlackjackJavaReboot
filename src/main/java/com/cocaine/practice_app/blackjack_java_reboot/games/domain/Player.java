package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public final class Player {

    private final Integer id;

    private final String name;
    private final String email;
    private final Boolean isDealer;

    @Setter(AccessLevel.PRIVATE)
    private PlayerState playerState;

    @Setter(AccessLevel.PRIVATE)
    private Collection<Card> hand;

    public Player(String name, String email) {
        this(null, name, email, false, PlayerState.PLAYING, new ArrayList<>());
    }

    void setStartingHand(Collection<Card> startingHand) {
        if (startingHand == null || startingHand.size() != 2)
            throw new IllegalArgumentException("startingHand must have exactly two cards.");

        setHand(startingHand);

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
