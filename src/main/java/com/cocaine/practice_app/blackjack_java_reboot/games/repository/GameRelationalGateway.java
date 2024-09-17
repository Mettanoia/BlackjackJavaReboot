package com.cocaine.practice_app.blackjack_java_reboot.games.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GameRelationalGateway extends ReactiveCrudRepository<GameEntity, Long> {
}
