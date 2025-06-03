package game.spells;

import game.Dungeon;
import game.entities.Entity;
import game.items.Equipment;

public class BoogieWoogie extends Spell{
    public BoogieWoogie() {
        super("BoogieWoogie");
    }


    @Override
    public void cast(Entity target){}

    @Override
    public void cast(Entity target1, Entity target2, Dungeon d) {
        d.switchEntities(target1, target2);
    }

    @Override
    public void cast(Equipment e){}
}
