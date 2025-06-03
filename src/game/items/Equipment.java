package game.items;

import game.entities.Statistics;

public abstract class Equipment {
    private String _name;
    private Statistics _bonusStats;

    protected Equipment(String name) {
        _name = name;
    }



    //? set les stats bonus
    public void setBonusStats(Statistics bonusStats) {
        this._bonusStats = bonusStats;
    }



    //? instanceof du pauvre, on va override pour return true dans les classes correspondantes
    public boolean isWeapon() {
        return false;
    }
    public boolean isArmor() {
        return false;
    }

    //? Getters
    public String getName(){
        return _name;
    }
    public Statistics getBonusStats() {
        return _bonusStats;
    }

    @Override
    public String toString(){
        return _name;
    }
}
