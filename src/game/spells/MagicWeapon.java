package game.spells;

import game.Dungeon;
import game.entities.Entity;
import game.items.Equipment;
import game.items.Weapon;
import game.utils.Display;

import static game.utils.GameUtils.askValidInt;
import static game.utils.GameUtils.scanner;

public class MagicWeapon extends Spell{
    public MagicWeapon() {
        super("MagicWeapon");
    }

    @Override
    public void cast(Equipment target) {
        if (target == null) {
            Display.displayError("No valid equipment selected for enchanting.");
            return;
        }

        ((Weapon) target).addBonus(1);


    }


    @Override
    public void cast(Entity target1, Entity target2, Dungeon d){}
    @Override
    public void cast(Entity target){}

    public Equipment selectEquipmentToEnchant(game.entities.Character character) {
        Display.display("Choose an item to enchant:");
        Display.display("1. Equipped Weapon: " + (character.getEquippedWeapon() != null ? character.getEquippedWeapon().getName() : "None"));
        Display.display("2. Inventory:");
        Display.display(character.displayInventory());

        int choice = askValidInt("Enter your choice (1 for equipped weapon, 2 for inventory item):", 1, 2);
        Equipment target = null;

        switch (choice) {
            case 1 -> {
                if (character.getEquippedWeapon() != null) {
                    target = character.getEquippedWeapon();
                } else {
                    Display.displayError("You don't have an equipped weapon.");
                }
            }
            case 2 -> {
                Display.display("Choose an item from your inventory:");
                int inventoryChoice = scanner.nextInt();
                if (inventoryChoice >= 0 && inventoryChoice < character.getInventory().size() && character.getInventory().get(inventoryChoice).isWeapon()) {
                    target = character.getInventory().get(inventoryChoice);
                } else {
                    Display.displayError("Invalid choice.");
                }
            }

            default -> Display.displayError("Invalid choice.");
        }
        return target;
    }
}
