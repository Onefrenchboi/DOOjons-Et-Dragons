package game.items;

import game.entities.Statistics;
import game.items.enums.*;

public abstract class Armor extends Equipment {
    private int _armorClass; // armor class tsais si t'es pas un nerd et que tu fais pas du dnd tt les vendredis en 202 (oui c'est une pub pour le club jdr d'emina)
    private ArmorType _armorType;

    protected Armor(String name, int AC, Statistics bonusStats, ArmorType armorType) {
        super(name, EquipmentType.ARMOR);
        this._armorType = armorType;
        this._armorClass = AC;
        this.setBonusStats(bonusStats);
    }





    //? To avoid InstanceOf
    @Override
    public boolean isArmor() {
        return true;
    }

    //? Getters
    public int getArmorClass() {
        return _armorClass;
    }
    public ArmorType getArmorType() {
        return _armorType;
    }
    @Override
    public String toString() {
        return getName() + " (AC: " + _armorClass + ")";
    }
}
