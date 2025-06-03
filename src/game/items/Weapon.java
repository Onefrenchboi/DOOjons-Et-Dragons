package game.items;

import game.entities.Statistics;
import game.items.enums.EquipmentType;
import game.items.enums.WeaponType;
import game.utils.GameUtils;

public abstract class Weapon extends Equipment{
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    private int _dicenum; //represents the number of dice to roll for damage
    private int _bonus; //pour le MagicWeapon
    private WeaponType _weaponType;

    protected Weapon(String name, int range, int dicenum, int damageroll, Statistics bonusStats,WeaponType weaponType) {
        super(name, EquipmentType.WEAPON);
        _range = range;
        _dicenum = dicenum;
        _damageroll = damageroll;
        this._bonus=0; //default bonus is 0
        this.setBonusStats(bonusStats);
        this._weaponType = weaponType;
    }



    //? To avoid InstanceOf
    @Override
    public boolean isWeapon() {
        return true;
    }


    //? Add a bonus to the weapon's damage
    public void addBonus(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative");
        }
        this._bonus += i;
    }

    //? Calculate the damage of the weapon
    public int damage() {
        return GameUtils.roll(_dicenum, _damageroll)+ _bonus;
    }


    //? Getters
    public int getBonus() {
        return _bonus;
    }
    public int getRange() {
        return _range;
    }
    public String getDamage() {
        return _dicenum + "d" + _damageroll;
    }
    public WeaponType getWeaponType() {
        return _weaponType;
    }



    @Override
    public String toString() {
        return getName() + " (Range: " + getRange() + ", Damage: " + getDamage() + ", Bonus: " + _bonus + ")";
    }
}
