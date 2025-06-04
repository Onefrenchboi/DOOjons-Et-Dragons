package game.entities.classes;

import game.entities.Statistics;
import game.items.Equipment;
import game.spells.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterClass {
    private String _name;
    private List<Equipment> _startingEquipment;
    private Statistics _bonusStats;
    private List<Spell> _spells;


    protected CharacterClass(String name, Statistics bonusStats, List<Equipment> startingEquipment) {
        this._name = name;
        this._bonusStats = bonusStats;
        this._startingEquipment = startingEquipment;
        this._spells = new ArrayList<>();
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

    public List<Spell> getSpells() {
        return _spells;
    }

    protected void addSpell(Spell spells) {
        this._spells.add(spells);
    }
}
