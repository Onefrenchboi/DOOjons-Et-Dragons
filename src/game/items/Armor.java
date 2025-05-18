package game.items;

import game.entities.Statistics;

public abstract class Armor extends Equipment {
    private String _name;
    private int _AC; // armor class tsais si t'es pas un nerd et que tu fais pas du dnd tt les vendredis en 202
    private Statistics _bonusStats;


    public Armor(String name, int AC, Statistics bonusStats) {
        super(name);
        this._AC = AC;
        this._bonusStats = bonusStats;
    }


    @Override
    public String toString() {
        return getName() + " (AC: " + _AC + ")";
    }
}
