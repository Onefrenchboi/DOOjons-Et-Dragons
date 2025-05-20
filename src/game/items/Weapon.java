package game.items;

import game.entities.Statistics;

public abstract class Weapon extends Equipment{
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    private int _dicenum; //represents the number of dice to roll for damage
    private Statistics _bonusStats;


    protected Weapon(String name, int range, int dicenum, int damageroll, Statistics bonusStats) {
        super(name);
        _range = range;
        _dicenum = dicenum;
        _damageroll = damageroll;
        _bonusStats = bonusStats;
    }


    public int getRange() {
        return _range;
    }
    public String getDamage() {
        return _dicenum + "d" + _damageroll;
    }

    @Override
    public String toString() {
        return getName() + " (Range: " + getRange() + ", Damage: " + getDamage() + ")";
    }
}
