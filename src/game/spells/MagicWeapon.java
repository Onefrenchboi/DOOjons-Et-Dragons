package game.spells;

import game.entities.Entity;
import game.entities.EntityType;
import game.items.Equipment;
import game.items.Weapon;
import game.utils.Display;

import java.util.HashMap;

import static game.utils.GameUtils.askValidInt;
import static game.utils.GameUtils.scanner;

public class MagicWeapon extends Spell {
    public MagicWeapon() {
        super("MagicWeapon");
    }
    /**
     * add +1 bonus to an equipment chosen by the user.
     * The equipment can be either an equipped item or an item from the character's inventory.
     *
     * @param entitiesPos The list of entities
     * @param targetChar the target
     */
    public void cast(HashMap<Entity, int[]> entitiesPos, Entity targetChar) {
        if (targetChar.getType()== EntityType.MONSTER) {
            Display.displayError("You can't enchant a monster.");
            return;
        }
        Display.display("Choose an item to enchant:");
        Display.display("1. Equipped Weapon: " + (targetChar.getEquippedWeapon() != null ? targetChar.getEquippedWeapon().getName() : "None"));
        Display.display("2. Inventory:");
        Display.display(targetChar.displayInventory());

        int choice = askValidInt("Enter your choice (1 for equipped weapon, 2 for inventory item):", 1, 2);
        Equipment target = null;

        switch (choice) {
            case 1 -> {
                if (targetChar.getEquippedWeapon() != null) {
                    target = targetChar.getEquippedWeapon();
                } else {
                    Display.displayError("You don't have an equipped weapon.");
                }
            }
            case 2 -> {
                Display.display("Choose an item from your inventory:");
                int inventoryChoice = scanner.nextInt();
                if (inventoryChoice >= 0 && inventoryChoice < targetChar.getInventory().size() && targetChar.getInventory().get(inventoryChoice).getType() == game.items.enums.EquipmentType.WEAPON) {
                    target = targetChar.getInventory().get(inventoryChoice);
                } else {
                    Display.displayError("Invalid choice.");
                }
            }

            default -> Display.displayError("Invalid choice.");
        }


        ((Weapon) target).addBonus(1);
    }

}
