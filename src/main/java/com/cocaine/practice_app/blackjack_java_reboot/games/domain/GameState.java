package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Stack;


@Getter
@RequiredArgsConstructor
final class GameState {

    private final Stack<Card> deck;
    private final List<Player> players;
    private final Collection<Card> dealersHand;

}
