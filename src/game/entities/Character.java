package game.entities;

import game.entities.races.*;
import game.entities.classes.*;
import game.items.*;

import java.util.List;

public class Character {
    private String _name;
    private Race _race;
    private CharacterClass _class;
    private Statistics _stats;
    private List<Equipment> _inventory;
    private Weapon _equippedWeapon;
    private Armor _equippedArmor;

    public Character(String name, Race race, CharacterClass charclass) {
        this._name = name;
        this._race = race;
        this._class = charclass;
        this._stats = new Statistics(); //TODO initialize avec les lancés de dés
        this._stats.addStatistics(_class.getBonusStats());
        this._stats.addStatistics(_race.getBonusStats());

    }

}
