package com.cocaine.practice_app.blackjack_java_reboot.games.use_cases;

import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Card;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Game;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.GameState;
import com.cocaine.practice_app.blackjack_java_reboot.games.domain.Player;
import com.cocaine.practice_app.blackjack_java_reboot.games.endpoints.GameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static reactor.core.publisher.Mono.empty;

@Configuration
class GamesUseCasesConfig {

    @Bean
    public NewGameUseCase newGameUseCase(GameRepository gameRepository) {

        return new NewGameUseCase() {

            @Override
            public Mono<Game> exec() {

                Deque<Card> deck = getDeck();

                GameState gameState = new GameState(deck, new ArrayList<>(List.of(getDealer())));

                Game game = new Game(gameState);

                return gameRepository.save(game);

            }

            private static Player getDealer() {
                return new Player(null,"Dealer", "dealer.777.es", true);
            }

            private static Deque<Card> getDeck() {

                Deque<Card> deck = new ArrayDeque<>();

                for (Card.Suit suit : Card.Suit.values()) {
                    for (Card.Rank rank : Card.Rank.values()) {
                        deck.add(new Card(rank, suit));
                    }
                }

                return deck;

            }

        };

    }

}
