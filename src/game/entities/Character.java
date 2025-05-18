package game.entities;

import game.Dungeon;
import game.entities.races.*;
import game.entities.classes.*;
import game.items.*;

import java.util.List;

public class Character extends Entity {
    private Race _race;
    private CharacterClass _class;
    private List<Equipment> _inventory;
    private Weapon _equippedWeapon;
    private Armor _equippedArmor;

    public Character(String name, Race race, CharacterClass charclass) {
        super(name);
        this._race = race;
        this._class = charclass;

        this.getStats().addStatistics(_race.getBonusStats());
        this.getStats().addStatistics(_class.getBonusStats());
        this.setPseudo(this.getName().length() >= 3 ? this.getName().substring(0, 3) : this.getName());
    }





    @Override
    public String toString() {
        return this.getName() + " the " + _class.getName() + " " + _race.getName();
    }


}
