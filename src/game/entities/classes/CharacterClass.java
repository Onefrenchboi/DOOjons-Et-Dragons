package game.entities.classes;

import game.entities.Statistics;
import game.items.Equipment;

import java.util.List;

public abstract class CharacterClass {
    private String _name;
    private List<Equipment> _startingEquipment;
    private Statistics _bonusStats;


    protected CharacterClass(String name, Statistics bonusStats, List<Equipment> startingEquipment) {
        this._name = name;
        this._bonusStats = bonusStats;
        this._startingEquipment = startingEquipment;
    }

    public List<Equipment> getStartingEquipment() {
        return _startingEquipment;
    }

    public Statistics getBonusStats() {
        return _bonusStats;
    }

    public String getName() {
        return _name;
    }


}
