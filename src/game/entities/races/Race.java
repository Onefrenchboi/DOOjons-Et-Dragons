package game.entities.races;

import game.entities.Statistics;

public abstract class Race {
    private String _name;
    private Statistics _bonusStats;


    protected Race(String name, Statistics bonusStats) {
        this._name = name;
        this._bonusStats = bonusStats;
    }

    public Statistics getBonusStats() {
        return _bonusStats;
    }

    public String getName() {
        return _name;
    }
}
