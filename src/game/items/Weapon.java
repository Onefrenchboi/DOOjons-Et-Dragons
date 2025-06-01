package game.items;

import game.entities.Statistics;
import game.utils.GameUtils;

public abstract class Weapon extends Equipment{
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    private int _dicenum; //represents the number o


    protected Weapon(String name, int range, int dicenum, int damageroll, Statistics bonusStats) {
        super(name);
        _range = range;
        _dicenum = dicenum;
        _damageroll = damageroll;
        this.setBonusStats(bonusStats);
    }


    public int getRange() {
        return _range;
    }
    public String getDamage() {
        return _dicenum + "d" + _damageroll;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }
    @Override
    public String toString() {
        return getName() + " (Range: " + getRange() + ", Damage: " + getDamage() + ")";
    }

    public int damage() {
        return GameUtils.roll(_dicenum, _damageroll);
    }
}
