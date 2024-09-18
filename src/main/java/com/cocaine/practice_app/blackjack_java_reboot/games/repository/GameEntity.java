package com.cocaine.practice_app.blackjack_java_reboot.games.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "games")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class GameEntity {

    @Id
    @Getter
    private final Integer Id;

}
