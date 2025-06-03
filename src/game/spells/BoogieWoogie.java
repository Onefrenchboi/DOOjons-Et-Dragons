package game.spells;

import game.Dungeon;
import game.entities.Entity;
import game.items.Equipment;

public class BoogieWoogie extends Spell{
    public BoogieWoogie() {
        super("BoogieWoogie");
    }


    /**
     * Switches the positions of two entities in the dungeon.
     *.
     *
     * @param target1 The first entity
     * @param target2 The second entity
     * @param d The dungeon where they are
     */
    @Override
    public void cast(Entity target1, Entity target2, Dungeon d) {
        d.switchEntities(target1, target2);
    }

    @Override
    public void cast(Equipment e){}
    @Override
    public void cast(Entity target){}

}
