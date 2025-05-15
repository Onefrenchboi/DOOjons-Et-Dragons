package game.entities;

import game.entities.races.*;
import game.entities.classes.*;
import game.items.*;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {
    private String _name;
    private Race _race;
    private CharacterClass _class;
    private List<Equipment> _inventory;
    private Weapon _equippedWeapon;
    private Armor _equippedArmor;

    public Character(String name, Race race, CharacterClass charclass) {
        this._name = name;
        this._race = race;
        this._class = charclass;

        this.getStats().addStatistics(_race.getBonusStats());
        this.getStats().addStatistics(_class.getBonusStats());
    }
    public String getName() {
        return _name;
    }


}
