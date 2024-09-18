package com.cocaine.practice_app.blackjack_java_reboot.games.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "players")
@RequiredArgsConstructor
final class PlayerEntity {

    @Id
    private final Integer id;

    private final String name;
    private final String email;

}
