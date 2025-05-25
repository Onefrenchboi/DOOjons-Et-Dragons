package game.entities;
import game.entities.races.*;
import game.entities.classes.*;
import game.items.*;
import game.utils.GameUtils;
import game.utils.Display;

import java.util.ArrayList;
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
        this._inventory = new ArrayList<Equipment>();

        this.getStats().addStatistics(_race.getBonusStats());
        this.getStats().addStatistics(_class.getBonusStats());

        //? Handling du pseudo pour garder l'alignement de la map
        if (name.length() == 1) {
            this.setPseudo(" " + name + " ");
        } else if (name.length() == 2) {
            this.setPseudo(name + " ");
        } else {
            this.setPseudo(name.substring(0, 3));
        }

        this.setMaxHp(this.getHp());

    }


    public String getInfo(){
        return  "   HP ❤ : " + this.getHp() + "/" + this.getMaxHp() + "\n" +
                "   Armor ⊙ : " + (this._equippedArmor != null ? this._equippedArmor.getName() : "None") + "\n" +
                "   Weapon ⚔ : " + (this._equippedWeapon != null ? this._equippedWeapon.getName() : "None") + "\n" +
                "   Inventory ◼ : " + this.getInventory() + "\n" +
                "   STR ✪ : " + this.getStats().getStrength() + "\n" +
                "   DEX ➔ : " + this.getStats().getDexterity() + "\n" +
                "   SPD ⚡ : " + this.getStats().getSpeed() + "\n";

    }

    public List<Equipment> getInventory() {
        for (Equipment equipment : _inventory) {
            int n = _inventory.indexOf(equipment);
            Display.display("[" + n + "]" + equipment.toString());
        }
        return _inventory;
    }
    @Override
    public String toString() {
        return GameUtils.PURPLE + super.getName() + " the " + _class.getName() + " " + _race.getName() + GameUtils.RESET;
    }

}
