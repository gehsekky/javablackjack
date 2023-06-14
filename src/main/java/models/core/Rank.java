package models.core;

import java.util.stream.Stream;

public enum Rank {
    Ace("Ace", 1, 11),
    Two("Two", 2, 2),
    Three("Three", 3, 3),
    Four("Four", 4, 4),
    Five("Five", 5, 5),
    Six("Six", 6, 6),
    Seven("Seven", 7, 7),
    Eight("Eight", 8, 8),
    Nine("Nine", 9, 9),
    Ten("Ten", 10, 10),
    Jack("Jack", 11, 10),
    Queen("Queen", 12, 10),
    King("King", 13, 10);

    public final String label;
    public final Integer order;
    public final Integer value;

    private Rank(String label, Integer order, Integer value) {
        this.label = label;
        this.order = order;
        this.value = value;
    }

    public static Stream<Rank> stream() {
        return Stream.of(Rank.values());
    }
}
