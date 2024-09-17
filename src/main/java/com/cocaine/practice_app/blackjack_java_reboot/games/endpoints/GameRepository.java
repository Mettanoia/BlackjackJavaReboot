package com.cocaine.practice_app.blackjack_java_reboot.games.endpoints;

import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Game;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.GameState;
import com.cocaine.practice_app.blackjack_java_reboot.games.repository.GameEntity;
import com.cocaine.practice_app.blackjack_java_reboot.games.repository.GameRelationalGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
final class GameRepository {

    private final GameRelationalGateway gameRelationalGateway;

    public Mono<Game> save(Game game) {

        GameState gameState = game.getGameState();

        return gameRelationalGateway.save(new GameEntity()).map(gameEntity ->  entityToDomain(gameEntity, gameState));

    }



    // Mapper functions

    private static Game entityToDomain(GameEntity gameEntity, GameState gameState) {
        return new Game(Math.toIntExact((gameEntity.getId())), gameState);
    }

}
