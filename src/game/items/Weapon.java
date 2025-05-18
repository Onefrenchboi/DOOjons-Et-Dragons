package game.items;

import game.entities.Statistics;

public abstract class Weapon extends Equipment{
    private String _name;
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    // private int _dicenum; //represents the number of dice to roll for damage //TODO : implement this
    private Statistics _bonusStats;


    protected Weapon(String name, int range, int damageroll, Statistics bonusStats) {
        super(name);
        _range = range;
        _damageroll = damageroll;
        _bonusStats = bonusStats;
    }


    public int getRange() {
        return _range;
    }
    public int getDamage() {
        return _damageroll;
    }

    @Override
    public String toString() {
        return getName() + " (Range: " + getRange() + ", Damage: " + getDamage() + ")";
    }
}
