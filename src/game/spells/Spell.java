package game.spells;

import game.entities.Entity;

import java.util.HashMap;

public abstract class Spell {
    private String _name;

    protected Spell(String name) {
        this._name = name;
    }


    public String getName() {
        return _name;
    }

    public abstract void cast(HashMap<Entity, int[]> entitiesPos, Entity target);


}
