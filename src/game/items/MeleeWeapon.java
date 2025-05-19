package game.items;

import game.entities.Statistics;

public class MeleeWeapon extends Weapon{
    public MeleeWeapon(String name, int range, int dicenum, int damageroll) {
        super(name, range, dicenum ,damageroll, new Statistics(0, 0, 0, 0, 0));
    }
}
