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

    public Character(String name, Race race, CharacterClass charclass) {
        super(name);
        this._race = race;
        this._class = charclass;
        this._inventory = new ArrayList<>(charclass.getStartingEquipment());

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


    @Override
    public boolean isPlayer() {
        return true;
    }

    public String displayInventory() {
        if (_inventory == null || _inventory.isEmpty()) {
            return "Inventory is empty.";
        }
        StringBuilder inventoryDisplay = new StringBuilder();
        for (Equipment equipment : _inventory) {
            int n = _inventory.indexOf(equipment);
            inventoryDisplay.append("[" + n + "] ").append(equipment.getName()).append(" | ");
        }
        return inventoryDisplay.toString();
    }

    public List<Equipment> getInventory() {
        return _inventory;
    }

    @Override
    public void equipWeapon(Equipment equipment){
        this.setEquippedWeapon((Weapon) equipment);
    }

    @Override
    public void equipArmor(Equipment equipment){
        this.setEquippedArmor((Armor) equipment);
    }


    public String getInfo(){
        return  "   HP ❤ : " + this.getHp() + "/" + this.getMaxHp() + "\n" +
                "   Armor ⊙ : " + (this.getEquippedArmor() != null ? this.getEquippedArmor().getName() : "None") + "\n" +
                "   Weapon ⚔ : " + (this.getEquippedWeapon() != null ? this.getEquippedWeapon().getName() : "None") + "\n" +
                "   Inventory ◼ : " + this.displayInventory() + "\n" +
                "   STR ✪ : " + this.getStats().getStrength() + "\n" +
                "   DEX ➔ : " + this.getStats().getDexterity() + "\n" +
                "   SPD ⚡ : " + this.getStats().getSpeed();
    }
    @Override
    public String toString() {
        return GameUtils.PURPLE + super.getName() + " the " + _class.getName() + " " + _race.getName() + GameUtils.RESET;
    }
}
