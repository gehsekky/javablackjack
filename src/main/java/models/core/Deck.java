package models.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    /**
     * constructor
     */
    public Deck() {
        // load cards
        this.cards = new ArrayList<>();
        Suit.stream().forEach((s) -> {
            Rank.stream().forEach((r) -> {
                this.cards.add(new Card(s, r));
            });
        });
        shuffle();
    }

    /**
     * shuffles deck to achieve randomness
     */
    public void shuffle() {
        // In 1992, Bayer and Diaconis showed that after seven random riffle shuffles of a deck of 52 cards, every
        // configuration is nearly equally likely. Shuffling more than this does not significantly increase the
        // “randomness”; shuffle less than this and the deck is “far” from random.
        for (int i = 0; i < 7; i++) {
            Collections.shuffle(this.cards);
        }
    }

    /**
     * draws "top" card from deck
     * @return the drawn card
     */
    public Card drawCard() {
        return this.cards.remove(0);
    }
}
