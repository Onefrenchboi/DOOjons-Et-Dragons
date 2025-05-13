package game.items;

import game.entities.Statistics;

public abstract class Weapon extends Equipment{
    private String _name;
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    // private int _dicenum; //represents the number of dice to roll for damage //TODO : implement this
    private Statistics _bonusStats;


    public Weapon(String name, int range, int damageroll, Statistics bonusStats) {
        super(name);
        _range = range;
        _damageroll = damageroll;
        _bonusStats = bonusStats;
    }
}
