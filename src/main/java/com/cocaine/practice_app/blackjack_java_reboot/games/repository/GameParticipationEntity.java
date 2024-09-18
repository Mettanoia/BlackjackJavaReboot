package com.cocaine.practice_app.blackjack_java_reboot.games.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "game_participants")
@Getter
@RequiredArgsConstructor
public final class GameParticipationEntity {

    @Id
    private final Integer id;

    @Column("player_id")
    private final Integer playerId;

    @Column("game_id")
    private final Integer gameId;

}
