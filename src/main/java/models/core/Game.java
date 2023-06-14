package models.core;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck deck;

    /**
     * constructor
     */
    public Game() {
        this.deck = new Deck();
        this.dealerHand = new ArrayList<>();
        this.playerHand = new ArrayList<>();
    }

    private List<Card> dealerHand;

    public List<Card> getDealerHand() {
        return this.dealerHand;
    }

    private List<Card> playerHand;

    public List<Card> getPlayerHand() {
        return this.playerHand;
    }

    /**
     * deal first hand
     */
    public void deal() {
        for (int i = 0; i < 2; i++) {
            this.playerHand.add(this.deck.drawCard());
            this.dealerHand.add(this.deck.drawCard());
        }
    }

    /**
     * hit for player
     */
    public void playerHit() {
        this.playerHand.add(this.deck.drawCard());
    }

    /**
     * hit for dealer
     */
    public void dealerHit() {
        this.dealerHand.add(this.deck.drawCard());
    }
}
