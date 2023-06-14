package models.core;

import java.util.stream.Stream;

public enum Participant {
    Player("player"),
    Dealer("dealer");

    public final String label;

    private Participant(String label) {
        this.label = label;
    }

    public static Stream<Participant> stream() {
        return Stream.of(Participant.values());
    }
}
