package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @ParameterizedTest
    @MethodSource("provideCardRankTestData")
    void testCardRankGetValue(Card.Rank rank, Boolean high, int expectedValue) {
        assertEquals(expectedValue, rank.getValue(high));
    }

    static Stream<Arguments> provideCardRankTestData() {

        return Stream.of(

                // Test non-Ace cards
                Arguments.of(Card.Rank.TWO, true, 2),
                Arguments.of(Card.Rank.TWO, false, 2),
                Arguments.of(Card.Rank.TEN, true, 10),
                Arguments.of(Card.Rank.KING, true, 10),
                Arguments.of(Card.Rank.JACK, false, 10),

                // Test Ace card
                Arguments.of(Card.Rank.ACE, true, 11), // High value (11)

                Arguments.of(Card.Rank.ACE, false, 1)  // Low value (1)

        );

    }

}
