package game.items;

import game.entities.Statistics;

public abstract class Armor extends Equipment {
    private int _AC; // armor class tsais si t'es pas un nerd et que tu fais pas du dnd tt les vendredis en 202 (oui c'est une pub pour le club jdr d'emina)

    protected Armor(String name, int AC, Statistics bonusStats) {
        super(name);
        this._AC = AC;
        this.setBonusStats(bonusStats);
    }


    //? To avoid InstanceOf
    @Override
    public boolean isArmor() {
        return true;
    }

    //? Getters
    public int getAC() {
        return _AC;
    }

    @Override
    public String toString() {
        return getName() + " (AC: " + _AC + ")";
    }
}
