package com.cocaine.practice_app.blackjack_java_reboot.games.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;


    @BeforeEach
    void setUp() {
        player = new Player("John Doe", "john.doe@example.com");
    }


    @ParameterizedTest
    @MethodSource("provideStartingHandData")
    void testSetStartingHand(Collection<Card> startingHand, boolean expectException, Player.PlayerState expectedState) {

        if (expectException) {

            assertThrows(IllegalArgumentException.class, () -> player.setStartingHand(startingHand));

        } else {

            player.setStartingHand(startingHand);

            assertEquals(2, player.getHand().size());
            assertEquals(expectedState, player.getPlayerState());

        }
    }

    @ParameterizedTest
    @MethodSource("provideHitCardData")
    void testHit(Card initialCard1, Card initialCard2, Card hitCard, Player.PlayerState expectedState, Class<? extends Exception> expectedException) {

        Collection<Card> startingHand = new HashSet<>(2);

        startingHand.addAll(List.of(initialCard1, initialCard2));

        player.setStartingHand(startingHand);

        if (expectedException != null) {

            assertThrows(expectedException, () -> player.hit(hitCard));

        } else {

            player.hit(hitCard);
            assertEquals(expectedState, player.getPlayerState());

        }

    }

    @ParameterizedTest
    @MethodSource("provideHandValue")
    void testCalculateHandValueWithThirdCard(Collection<Card> startingHand, boolean addThirdCard, Card thirdCard, int expectedValue) {

        player.setStartingHand(startingHand);

        if (addThirdCard)
            player.hit(thirdCard);

        assertEquals(expectedValue, player.calculateHandValue());

    }

    @ParameterizedTest
    @MethodSource("provideBlackjackHandData")
    void testHasBlackjack(Collection<Card> hand, boolean expectedHasBlackjack) {
        player.setStartingHand(hand);
        assertEquals(expectedHasBlackjack, player.hasBlackjack());
    }

    @Test
    void testStand() {
        player.stand();
        assertEquals(Player.PlayerState.STANDING, player.getPlayerState());
    }


    // Sources

    static Stream<Arguments> provideStartingHandData() {

        return Stream.of(

                // Valid hand with Blackjack (Ace + 10)
                Arguments.of(
                        List.of(new Card(Card.Rank.ACE, Card.Suit.SPADES), new Card(Card.Rank.TEN, Card.Suit.HEARTS)),
                        false,
                        Player.PlayerState.BLACKJACK
                ),

                // Valid hand without Blackjack (2 + 3)
                Arguments.of(
                        List.of(new Card(Card.Rank.TWO, Card.Suit.CLUBS), new Card(Card.Rank.THREE, Card.Suit.DIAMONDS)),
                        false,
                        Player.PlayerState.PLAYING
                ),

                // Invalid hand (1 card)
                Arguments.of(
                        List.of(new Card(Card.Rank.TWO, Card.Suit.CLUBS)),
                        true,
                        null
                ),

                // Invalid hand (3 cards)
                Arguments.of(
                        List.of(new Card(Card.Rank.TWO, Card.Suit.CLUBS), new Card(Card.Rank.THREE, Card.Suit.DIAMONDS), new Card(Card.Rank.FIVE, Card.Suit.HEARTS)),
                        true,
                        null
                ),

                // Null hand (throws exception)
                Arguments.of(
                        null,
                        true,
                        null
                )

        );
    }

    static Stream<Arguments> provideBlackjackHandData() {
        return Stream.of(
                // Player has Blackjack
                Arguments.of(
                        List.of(new Card(Card.Rank.ACE, Card.Suit.SPADES), new Card(Card.Rank.TEN, Card.Suit.HEARTS)),
                        true // Total: Ace + 10 = 21 (Blackjack)
                ),
                // Player does not have Blackjack
                Arguments.of(
                        List.of(new Card(Card.Rank.NINE, Card.Suit.CLUBS), new Card(Card.Rank.TWO, Card.Suit.HEARTS)),
                        false // Total: 9 + 2 = 11 (Not Blackjack)
                )
        );
    }

    static Stream<Arguments> provideHandValue() {

        Collection<Card> noAceNoBurstNoThirdCard = new HashSet<>(2);
        noAceNoBurstNoThirdCard.addAll(List.of(
                new Card(Card.Rank.NINE, Card.Suit.CLUBS),
                new Card(Card.Rank.SEVEN, Card.Suit.HEARTS)
        ));

        Collection<Card> closestToBlackjack = new HashSet<>(2);
        closestToBlackjack.addAll(List.of(
                new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS)
        ));

        Collection<Card> noBurst = new HashSet<>(2);
        noBurst.addAll(List.of(
                new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                new Card(Card.Rank.NINE, Card.Suit.HEARTS)
        ));

        return Stream.of(

                // Hand doesn't exceed 21, no Ace adjustment needed, no third card
                Arguments.of(
                        noAceNoBurstNoThirdCard,
                        false, // No third card to be added
                        null,  // Third card is null
                        16     // Total: 9 + 7 = 16
                ),

                // Hand doesn't exceed 21, Ace needs to be adjusted, third card added
                Arguments.of(
                        noBurst,
                        true,  // Third card will be added using hit()
                        new Card(Card.Rank.TEN, Card.Suit.SPADES), // Third card added via hit()
                        20     // Total: Ace (1) + 9 + 10 = 20
                ),

                // Two Aces, combination closest to 21
                Arguments.of(
                        closestToBlackjack,
                        true,  // Third card will be added using hit()
                        new Card(Card.Rank.NINE, Card.Suit.SPADES), // Third card added via hit()
                        21     // Total: Ace (11) + Ace (1) + 9 = 21
                )

        );

    }

    static Stream<Arguments> provideHitCardData() {
        return Stream.of(

                // Valid card hit without busting
                Arguments.of(
                        new Card(Card.Rank.TWO, Card.Suit.HEARTS),
                        new Card(Card.Rank.THREE, Card.Suit.SPADES),
                        new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                        Player.PlayerState.PLAYING, // Player state should not change
                        null // No exception expected
                ),

                // Hit causing bust
                Arguments.of(
                        new Card(Card.Rank.TEN, Card.Suit.HEARTS),
                        new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                        new Card(Card.Rank.FOUR, Card.Suit.CLUBS),
                        Player.PlayerState.BUSTED, // Player state should become BUSTED
                        null // No exception expected
                ),

                // Null card causing exception
                Arguments.of(
                        new Card(Card.Rank.TWO, Card.Suit.HEARTS),
                        new Card(Card.Rank.THREE, Card.Suit.SPADES),
                        null, // Null card
                        Player.PlayerState.PLAYING, // No state change
                        NullPointerException.class // Expect NullPointerException
                )

        );
    }

}
