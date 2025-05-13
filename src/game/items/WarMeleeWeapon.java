package game.items;

import game.entities.Statistics;

public class WarMeleeWeapon extends Weapon{
    public WarMeleeWeapon(String name, int range, int damageroll) {
        super(name, range, damageroll, new Statistics(0, 4, 0, -2, 0));
    }

}
