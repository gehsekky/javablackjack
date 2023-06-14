package models.core;

public class Card implements Comparable<Card> {

    private Suit suit;
    public Suit getSuit() {
        return this.suit;
    }

    private Rank rank;

    public Rank getRank() {
        return this.rank;
    }

    private Integer value;

    public Integer getValue() {
        return this.value;
    }

    /**
     * constructor
     * @param suit
     * @param rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;
    }

    /**
     * change the value of an ace
     */
    public void swapAce() {
        if (this.rank != Rank.Ace) {
            throw new Error("cannot swap ace on card that is not ace");
        }

        if (this.value == 1) {
            this.value = 11;
        } else {
            this.value = 1;
        }
    }

    /**
     * print formatted card
     * @return stringified card value
     */
    public String toString() {
        return this.rank.label + " of " + this.suit.label;
    }

    /**
     * custom comparator
     * @param o the object to be compared.
     * @return comparison value
     */
    @Override
    public int compareTo(Card o) {
        if (this.rank.order > o.rank.order) return 1;
        if (this.rank.order == o.rank.order) return 0;
        return -1;
    }
}
