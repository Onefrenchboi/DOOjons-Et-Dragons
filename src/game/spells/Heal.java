package game.spells;

import game.Dungeon;
import game.entities.Character;
import game.entities.Entity;
import game.items.Equipment;
import game.utils.Display;
import game.utils.GameUtils;

public class Heal extends Spell {
    public Heal() {
        super("Heal");
    }


    /**
     * Heals the target entity for a 1d10 hp
     * If the target is a monster, then you cant heal it.
     *
     * @param target The entity to heal.
     */
    @Override
    public void cast(Entity target) {
        int healingAmount = GameUtils.roll(1, 10);
        if (target.isMonster()) {
            Display.displayError("You cannot heal monsters!");
        } else {
            int missingHp = target.getMaxHp() - target.getHp();
            int healAmountToApply = Math.min(healingAmount, missingHp);
            target.setHp(healAmountToApply);
            Display.display(GameUtils.GREEN + "You heal " + target.getName() + " for " + healAmountToApply + " hp!" + GameUtils.RESET);
        }

    }

    @Override
    public void cast(Entity target1, Entity target2, Dungeon d){}

    @Override
    public void cast(Equipment e){}
}

