package game.items.equipments;

import game.entities.Statistics;
import game.items.Weapon;

public class MeleeWeapon extends Weapon {
    public MeleeWeapon(String name, int range, int dicenum, int damageroll) {
        super(name, range, dicenum ,damageroll, new Statistics(0, 0, 0, 0, 0));
    }
}
