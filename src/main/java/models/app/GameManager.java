package models.app;

import models.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameManager {
    private Game game;

    private GameResult result;

    private Integer playerMoney;

    /**
     * constructor
     */
    public GameManager() {
        this.playerMoney = 100;
    }

    /**
     * print program intro
     */
    public void printIntro() {
        // output program intro
        System.out.print("welcome to java blackjack.\n");
    }

    /**
     * perform exit program functions
     */
    private void exitProgram() {
        System.out.print("thanks for playing.");
        System.exit(0);
    }

    /**
     * fetch user input from terminal
     * @return user input
     */
    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    /**
     * print cards for the current moment in the game
     */
    private void printCards() {
        System.out.print("you have:\n");
        List<Card> playerHand = this.game.getPlayerHand();
        playerHand.forEach(c -> System.out.print(c.toString() + "\n"));
        System.out.print("\ndealer has:\n");
        List<Card> dealerHand = this.game.getDealerHand();
        for (int i = 0; i < dealerHand.size(); i++) {
            if (i == 0) {
                System.out.print("hole card\n");
            } else {
                System.out.print(dealerHand.get(i).toString() + "\n");
            }
        }
    }

    /**
     * sum up total of hand
     * @param hand
     * @return total of hand
     */
    private Integer getCardsTotal(List<Card> hand) {
        return hand.stream().mapToInt(c -> c.getValue()).sum();
    }

    /**
     * perform player stay action
     */
    private void stay() {
        Integer dealerTotal = getCardsTotal(this.game.getDealerHand());
        Integer playerTotal = getCardsTotal(this.game.getPlayerHand());
        this.result.setIsGameOver(true);
        while (dealerTotal < 17) {
            game.dealerHit();
            dealerTotal = getCardsTotal(this.game.getDealerHand());
        }
        System.out.print("you have " + playerTotal + "\n");
        System.out.print("dealer has " + dealerTotal + "\n");
        this.result.setTotals(buildTotals(dealerTotal, playerTotal));
    }

    /**
     * construct totals hashmap of player totals
     * @param dealerTotal
     * @param playerTotal
     * @return hashmap of player hand totals
     */
    private Map<String, Integer> buildTotals(Integer dealerTotal, Integer playerTotal) {
        HashMap totals = new HashMap<>();
        totals.put(Participant.Dealer.label, dealerTotal);
        totals.put(Participant.Player.label, playerTotal);
        return totals;
    }

    /**
     * return aces whose value have not been converted to 1
     * @param hand
     * @return list of unconverted ace cards
     */
    private List<Card> getUnconvertedAces(List<Card> hand) {
        return hand.stream().filter(c -> c.getRank() == Rank.Ace && c.getValue() == 11).collect(Collectors.toList());
    }

    /**
     * check hands for game ending situations
     */
    private void checkCards() {
        Integer playerTotal = getCardsTotal(this.game.getPlayerHand());
        Integer dealerTotal = getCardsTotal(this.game.getDealerHand());

        if (dealerTotal == 21 || playerTotal == 21) {
            this.result.setTotals(buildTotals(dealerTotal, playerTotal));
            this.result.setIsGameOver(true);
        }

        List<Card> playerUnconvertedAces = getUnconvertedAces(this.game.getPlayerHand());
        while (playerTotal > 21 && playerUnconvertedAces.size() > 0) {
            Card unconvertedAce = playerUnconvertedAces.remove(0);
            unconvertedAce.swapAce();
            playerTotal = getCardsTotal(this.game.getPlayerHand());
            playerUnconvertedAces = getUnconvertedAces(this.game.getPlayerHand());
        }
        if (playerTotal > 21) {
            this.result.setTotals(buildTotals(dealerTotal, playerTotal));
            this.result.setIsGameOver(true);
        }

        // dealers treat aces as 11
        if (dealerTotal > 21) {
            this.result.setTotals(buildTotals(dealerTotal, playerTotal));
            this.result.setIsGameOver(true);
        }
    }

    /**
     * process game result and print outcome
     * @param bet
     */
    private void processResults(Integer bet) {
        Map<String, Integer> totals = this.result.getTotals();
        Integer dealerTotal = totals.get(Participant.Dealer.label);
        Integer playerTotal = totals.get(Participant.Player.label);

        if (dealerTotal == playerTotal) {
            System.out.print("push. no one wins or loses.\n");
            return;
        }

        if (dealerTotal == 21) {
            System.out.print("dealer has blackjack. you lose.\n");
            playerMoney -= bet;
            return;
        }

        if (playerTotal == 21) {
            System.out.print("player has blackjack. you win.\n");
            playerMoney += bet;
            return;
        }

        if (playerTotal > 21) {
            System.out.print("player has busted. you lose.\n");
            playerMoney -= bet;
            return;
        }

        if (dealerTotal > 21) {
            System.out.print("dealer has busted. you win.\n");
            playerMoney += bet;
            return;
        }

        if (playerTotal > dealerTotal || dealerTotal > 21) {
            System.out.print("you win.\n");
            playerMoney += bet;
            return;
        }

        System.out.print("you lose.\n");
        playerMoney -= bet;
    }

    private void swapAce() {

    }

    /**
     * begin main game loop
     * @throws Exception
     */
    private void playNewGame() throws Exception {
        this.result = new GameResult();
        this.game = new Game();
        if (playerMoney <= 0) {
            playerMoney = 100;
            System.out.print("player is broke. going to atm.\n");
        }
        System.out.print("player total money: $" + this.playerMoney + "\n");
        boolean isValidInput = false;
        Integer bet = 0;
        while (!isValidInput) {
            System.out.print("please place a bet from 2-500: ");
            String betInput = getUserInput();
            bet = Integer.parseInt(betInput);
            if (bet <= this.playerMoney) {
                isValidInput = true;
            }
        }
        this.game.deal();
        // check if dealer or player has blackjack off the bat
        checkCards();
        printCards();
        while (!this.result.getIsGameOver()) {
            System.out.print("[1] hit, [2] stay, [3] exit program: ");
            String input = getUserInput();
            int userMenuSelection = Integer.parseInt(input);
            switch (userMenuSelection) {
                case 1:
                    this.game.playerHit();
                    printCards();
                    checkCards();
                    break;
                case 2:
                    stay();
                    break;
                case 3:
                    swapAce();
                    break;
                case 4:
                    exitProgram();
                    break;
                default:
                    throw new Exception("invalid selection: " + input);
            }
        }

        processResults(bet);
    }

    /**
     * begin game and main menu loop
     * @throws Exception
     */
    public void begin() throws Exception {
        try {
            printIntro();
            // begin main menu loop
            boolean exitProgram = false;
            while (!exitProgram) {
                System.out.print("[1] new game, [2] exit: ");
                String input = getUserInput();
                int userMenuSelection = Integer.parseInt(input);
                switch (userMenuSelection) {
                    case 1:
                        playNewGame();
                        break;
                    case 2:
                        exitProgram();
                        break;
                    default:
                        throw new Exception("unknown userMenuSelection: " + input);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.print("invalid input.\n");
            exitProgram();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
