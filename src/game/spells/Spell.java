package game.spells;

import game.Dungeon;
import game.entities.Entity;
import game.items.Equipment;

public abstract class Spell {
    private String _name;

    public Spell(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public abstract void cast(Entity target);
    public abstract void cast(Equipment target);
    public abstract void cast(Entity target1, Entity target2, Dungeon d);
}
