package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Deque;

/**
 * A class representing the state of  the game.<br /><br />
 *
 * This class has all members as mutable since it represents the constantly changing state
 * of the game.
 *
 */
@Getter
@RequiredArgsConstructor
public final class GameState {

    private final Deque<Card> deck;
    private final ArrayList<Player> players;
    private ArrayList<Player> winners = new ArrayList<>();
    private ArrayList<Player> pushedPlayers = new ArrayList<>();

}
