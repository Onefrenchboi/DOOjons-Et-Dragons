package game.items;

import game.entities.Statistics;

public abstract class Equipment {
    private String _name;
    private Statistics _bonusStats;

    protected Equipment(String name) {
        _name = name;
    }


    public String getName(){
        return _name;
    }


    public String toString(){
        return _name;
    }

    public Statistics getBonusStats() {
        return _bonusStats;
    }
    public void setBonusStats(Statistics bonusStats) {
        this._bonusStats = bonusStats;
    }



    //!encore un instanceof du pauvre
    public boolean isWeapon() {
        return false;
    }
    public boolean isArmor() {
        return false;
    }
}
