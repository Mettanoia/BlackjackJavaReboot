package com.cocaine.practice_app.blackjack_java_reboot.games.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static reactor.core.publisher.Mono.empty;

@Configuration
@RequiredArgsConstructor
class GameEndpointsConfig {

    private final GameRepository gameRepository;

    @Bean
    public RouterFunction<ServerResponse> gamesEndpoints() {

        return route(POST("/game/new"), this::createNewGame);

    }

    private Mono<ServerResponse> createNewGame(ServerRequest serverRequest) {

        // TODO - Create a new game, the game isn't persisted unless it is finished and hence has a list of winners this
        // TODO     method only makes it available at an application level so that it can be mutated and persisted.

        return empty();

    }

}
