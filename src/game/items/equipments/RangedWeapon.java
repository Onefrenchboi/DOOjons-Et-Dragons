package game.items.equipments;

import game.items.Weapon;
import game.items.enums.WeaponType;

public class RangedWeapon extends Weapon {
    public RangedWeapon(String name, int range,  int dicenum, int damageroll) {
        super(name, range, dicenum, damageroll, new game.entities.Statistics(0, 0, 0, 0, 0), WeaponType.RANGED);
    }

}
