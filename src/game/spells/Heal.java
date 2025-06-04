package game.spells;

import game.entities.Entity;
import game.entities.EntityType;
import game.utils.Display;
import game.utils.GameUtils;

import java.util.HashMap;

public class Heal extends Spell {
    public Heal() {
        super("Heal");
    }


    /**
     * Heals the target entity for a 1d10 hp
     * If the target is a monster, then you cant heal it.
     *
     * @param entitiesPos The list of entities, to find the one we want to heal
     * @param target the target
     */
    @Override
    public void cast(HashMap<Entity, int[]> entitiesPos, Entity target) {
        int healingAmount = GameUtils.roll(1, 10);
        if (target.getType()== EntityType.MONSTER) {
            Display.displayError("You cannot heal monsters!");
        } else {
            int missingHp = target.getMaxHp() - target.getHp();
            int healAmountToApply = Math.min(healingAmount, missingHp);
            target.setHp(healAmountToApply);
            Display.display(GameUtils.GREEN + "You heal " + target.getName() + " for " + healAmountToApply + " hp!" + GameUtils.RESET);
        }

    }
}


