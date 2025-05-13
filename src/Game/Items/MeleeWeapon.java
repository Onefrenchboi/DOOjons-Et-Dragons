package Game.Items;

import Game.Entities.Statistics;

public class MeleeWeapon extends Weapon{
    public MeleeWeapon(String name, int range, int damageroll) {
        super(name, range, damageroll, new Statistics(0, 0, 0, 0, 0));
    }
}
