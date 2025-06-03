package game.spells;

import game.Dungeon;
import game.entities.Character;
import game.entities.Entity;
import game.utils.Display;
import game.utils.GameUtils;

public class Heal extends Spell {
    public Heal() {
        super("Heal");
    }

    @Override
    public void cast(Entity target) {
        int healingAmount = GameUtils.roll(1, 10);
        if (target.isMonster()) {
            Display.displayError("You cannot heal monsters!");
        } else {
            int newHealth = target.getHp() + healingAmount;
            if (newHealth > target.getMaxHp()) {
                newHealth = target.getMaxHp() - target.getHp();
            }
            target.setHp(newHealth);
            Display.display(GameUtils.GREEN + "You heal " + target.getName() + " for " + healingAmount + " hp!" + GameUtils.RESET);
        }

    }

    @Override
    public void cast(Entity target1, Entity target2, Dungeon d){}
}

