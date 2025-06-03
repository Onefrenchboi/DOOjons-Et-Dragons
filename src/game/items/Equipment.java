package game.items;

import game.entities.Statistics;
import game.items.enums.EquipmentType;

public abstract class Equipment {
    private String _name;
    private Statistics _bonusStats;
    private EquipmentType _type;


    protected Equipment(String name, EquipmentType type) {
        _name = name;
        _type = type;
    }



    //? set les stats bonus
    public void setBonusStats(Statistics bonusStats) {
        this._bonusStats = bonusStats;
    }





    //? Getters
    public String getName(){
        return _name;
    }
    public Statistics getBonusStats() {
        return _bonusStats;
    }
    public EquipmentType getType() {
        return _type;
    }

    @Override
    public String toString(){
        return _name;
    }
}
