package Game.Entities.Classes;

import Game.Entities.Statistics;
import Game.Items.Equipment;

import java.util.List;

public abstract class CharacterClass {
    private String _name;
    private List<Equipment> _startingEquipment;
    private Statistics _bonusStats;


    public CharacterClass(String name, Statistics bonusStats) {
        this._name = name;
        this._bonusStats = bonusStats;
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
