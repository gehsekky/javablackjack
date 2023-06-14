package models.core;

import java.util.HashMap;
import java.util.Map;

public class GameResult {
    private Boolean isGameOver;

    public Boolean getIsGameOver() {
        return this.isGameOver;
    }

    public void setIsGameOver(Boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    private Map<String, Integer> totals;

    public Map<String, Integer> getTotals() {
        return this.totals;
    }

    public void setTotals(Map<String, Integer> totals) {
        this.totals = totals;
    }

    /**
     * constructor
     */
    public GameResult() {
        this.isGameOver = false;
        this.totals = new HashMap<>();
    }
}
