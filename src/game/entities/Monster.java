package game.entities;

import game.items.Equipment;
import game.items.Weapon;
import game.utils.Display;
import game.utils.GameUtils;

import java.util.List;

public class Monster extends Entity {
    private int _number;
    private int _AC;


    public Monster(String species, int number, Weapon weapon, int AC) {
        super(species, EntityType.MONSTER);
        this.setHp(GameUtils.roll(4, 4) + 3);
        this.setMaxHp(getHp());
        this._number = number;
        this._AC = AC;
        this.setEquippedWeapon(weapon);

        //? Handling du pseudo pour garder l'alignement de la map
        if (species.length() == 1) {
            this.setPseudo(species + "#" + number);
        } else if (species.length() == 2) {
            this.setPseudo(species + number);
        } else {
            this.setPseudo(species.substring(0, 2) + number);
        }
    }


    //? pour eviter le InstanceOf
    @Override
    public boolean isMonster() {
        return true;
    }


    //? Equipping weapons and armor is not allowed for monsters, donc on override en rien
    @Override
    public void equipWeapon(Equipment equipment) {
            Display.displayError("Monsters can't equip weapons.");

    }
    @Override
    public void equipArmor(Equipment equipment) {
        Display.displayError("Monsters can't equip armor.");
    }

    @Override
    public String displayInventory() {
        return "Monsters don't have an inventory.";
    }



    //? Getters
    @Override
    public String getInfo() {
        return  "   HP ❤ : " + this.getHp() + "/" + this.getMaxHp() + "\n" +
                "   Range : " + this.getEquippedWeapon().getRange() + "\n" +
                "   STR ✪ : " + this.getStats().getStrength() + "\n" +
                "   DEX ➔ : " + this.getStats().getDexterity() + "\n" +
                "   SPD ⚡ : " + this.getStats().getSpeed() + " (You can move " + this.getStats().getSpeed()/3 + " spaces per turn).";
    }
    public String getColor() {
        return GameUtils.RED;
    }
    @Override
    public List<Equipment> getInventory() {
        return null;
    }
    @Override
    public int getAC() {
        return _AC;
    }

    public EntityType getType() {
        return EntityType.MONSTER;
    }



    @Override
    public String toString() {
        return GameUtils.RED + super.getName() + " #" + _number + GameUtils.RESET;
    }
}