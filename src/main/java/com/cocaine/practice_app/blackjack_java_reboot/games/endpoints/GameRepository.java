package com.cocaine.practice_app.blackjack_java_reboot.games.endpoints;

import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Game;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.GameState;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Player;
import com.cocaine.practice_app.blackjack_java_reboot.games.repository.*;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public final class GameRepository {

    private final GameRelationalGateway gameRelationalGateway;
    private final GameParticipationGateway gameParticipationGateway;

    public Mono<Game> save(Game game) {

        GameState gameState = game.getGameState();

        return gameRelationalGateway.save(new GameEntity())

                // Save all the players in the join table and return the flux
                .flatMapMany(gameEntity ->
                        Flux.fromIterable(gameState.getPlayers().stream().filter(player -> !player.getIsDealer()).toList())
                                .flatMap(saveAllNonDealerPlayers(gameEntity)))

                .last() // All are saved for the same game, so we only need one

                // Return the game with the generated id
                .map(gameParticipationEntity -> new Game(gameParticipationEntity.getGameId(), gameState));

    }

    private Function<Player, Publisher<? extends GameParticipationEntity>> saveAllNonDealerPlayers(GameEntity gameEntity) {
        return player ->
                gameParticipationGateway.save(new GameParticipationEntity(null, player.getId(), gameEntity.getId()));
    }

}

