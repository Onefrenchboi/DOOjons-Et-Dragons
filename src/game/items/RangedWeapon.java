package game.items;

public class RangedWeapon extends Weapon{
    public RangedWeapon(String name, int range, int damageroll) {
        super(name, range, damageroll, new game.entities.Statistics(0, 0, 0, 0, 0));
    }

}
