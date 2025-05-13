package Game.Items;

public class RangedWeapon extends Weapon{
    public RangedWeapon(String name, int range, int damageroll) {
        super(name, range, damageroll, new Game.Entities.Statistics(0, 0, 0, 0, 0));
    }

}
