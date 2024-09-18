package com.cocaine.practice_app.blackjack_java_reboot.games.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GameParticipationGateway extends ReactiveCrudRepository<GameParticipationEntity, Integer> {
}
