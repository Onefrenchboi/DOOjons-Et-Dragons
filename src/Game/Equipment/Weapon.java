package Game.Equipment;

public abstract class Weapon extends Equipment{
    private int _range;
    private int _damageroll; //represents the dice to roll for damage
    // private int _dicenum; //represents the number of dice to roll for damage //TODO : implement this


    public Weapon(String name, int range, int damageroll) {
        super(name);
        _range = range;
        _damageroll = damageroll;
    }
}
