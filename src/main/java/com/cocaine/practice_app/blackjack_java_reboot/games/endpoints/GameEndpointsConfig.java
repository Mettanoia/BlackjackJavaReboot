package com.cocaine.practice_app.blackjack_java_reboot.games.endpoints;

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
class GameEndpointsConfig {

    @Bean
    public RouterFunction<ServerResponse> gamesEndpoints() {

        return route(POST("/game/new"), this::createNewGame);

    }

    private Mono<ServerResponse> createNewGame(ServerRequest serverRequest) {

        // TODO 1 - Create the new game using the gateway
        // TODO 2 - Answer the request with the appropriate response body

        return empty();

    }

}
