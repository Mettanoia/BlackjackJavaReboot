package com.cocaine.practice_app.blackjack_java_reboot.games.use_cases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static reactor.core.publisher.Mono.empty;

@Configuration
class GamesUseCasesConfig {

    @Bean
    public NewGameUseCase newGameUseCase() {
        return newGame -> empty();
    }

}
