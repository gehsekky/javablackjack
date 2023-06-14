package models.core;

import java.util.stream.Stream;

public enum Suit {
    Diamonds("Diamonds"),
    Hearts("Hearts"),
    Clubs("Clubs"),
    Spades("Spades");

    public final String label;

    private Suit(String label) {
        this.label = label;
    }

    public static Stream<Suit> stream() {
        return Stream.of(Suit.values());
    }
}
