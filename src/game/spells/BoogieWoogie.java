package game.spells;

import game.entities.Entity;
import game.items.Equipment;
import game.utils.Display;
import game.utils.GameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static game.utils.GameUtils.askValidInt;

public class BoogieWoogie extends Spell{
    public BoogieWoogie() {
        super("BoogieWoogie");
    }


    /**
     * Switches the positions of two entities in the dungeon.
     *.
     *
     * @param entitiesPos The list of entities
     * @param target the target
     */
    public void cast(HashMap<Entity, int[]> entitiesPos, Entity target) {
        if (!entitiesPos.containsKey(target)) {
            Display.displayError("error");
            return;
        }

        Display.display("Choose another entity to swap positions with:");
        List<Entity> selectableEntities = new ArrayList<>();

        for (Entity entity : entitiesPos.keySet()) {
            if (!entity.equals(target)) {
                selectableEntities.add(entity);
            }
        }

        for (int i = 0; i < selectableEntities.size(); i++) {
            Display.display("(" + i + ") " + selectableEntities.get(i));
        }

        int chosenIndex = askValidInt("Enter the number of the entity to swap positions with:", 0, selectableEntities.size() - 1);

        Entity chosenEntity = selectableEntities.get(chosenIndex);

        if (chosenEntity == null) {
            Display.displayError("Invalid choice.");
            return;
        }

        int[] targetPos = entitiesPos.get(target);
        int[] chosenPos = entitiesPos.get(chosenEntity);
        entitiesPos.put(target, chosenPos);
        entitiesPos.put(chosenEntity, targetPos);

        Display.display(GameUtils.PURPLE + "Swapped positions of " + target.getName() + " and " + chosenEntity.getName() + "." + GameUtils.RESET);
    }

}
