package game.spells;

import game.Dungeon;
import game.entities.Entity;
import game.utils.Display;

public class MagicWeapon extends Spell{
    public MagicWeapon() {
        super("Magic Weapon");
    }

    @Override
    public void cast(Entity target) {
//        if (target.isMonster()) {
//            Display.displayError("Cannot cast Magic Weapon on monsters!");
//        } else {
//
//
//        }


    }

    @Override
    public void cast(Entity target1, Entity target2, Dungeon d){}
}
