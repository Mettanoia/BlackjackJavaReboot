package com.cocaine.practice_app.blackjack_java_reboot.games.endpoints;

import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Game;
import com.cocaine.practice_app.blackjack_java_reboot.games.use_cases.NewGameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Configuration
@RequiredArgsConstructor
class GameEndpointsConfig {

    private final NewGameUseCase newGameUseCase;

    @Bean
    public RouterFunction<ServerResponse> gamesEndpoints() {

        return route(POST("/game/new"), this::createNewGame);

    }

    private Mono<ServerResponse> createNewGame(ServerRequest serverRequest) {

        return newGameUseCase.exec().flatMap(savedGame ->
                created(URI.create("/game/" + savedGame.getId()))
                        .body(savedGame, Game.class));

    }

}
