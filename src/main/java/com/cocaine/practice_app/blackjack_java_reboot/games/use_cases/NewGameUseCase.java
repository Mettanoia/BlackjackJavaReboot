package com.cocaine.practice_app.blackjack_java_reboot.games.use_cases;

import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Game;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface NewGameUseCase {
    Mono<Game> exec();
}
