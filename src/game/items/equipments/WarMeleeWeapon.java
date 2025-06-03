package game.items.equipments;

import game.entities.Statistics;
import game.items.Weapon;
import game.items.enums.WeaponType;

public class WarMeleeWeapon extends Weapon {
    public WarMeleeWeapon(String name, int range, int dicenum, int damageroll) {
        super(name, range,dicenum, damageroll, new Statistics(0, 4, 0, -2, 0), WeaponType.WARMELEE);
    }

}
