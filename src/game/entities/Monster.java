package game.entities;

import game.Dungeon;
import game.items.Weapon;
import game.utils.Utils;

public class Monster extends Entity {
    private int _number;
    private Weapon _weapon;





    public Monster(String species, int number, Weapon weapon) {
        super(species);
        this.setHp(Utils.roll(4, 4) + 3);
        this.setMaxHp(getHp());
        this._number = number;
        this._weapon = weapon;
        this.setPseudo(this.getName().length() >= 3 ? this.getName().substring(0, 2) + number : this.getName() + number);

    }



    @Override
    public String getColor() {
        return Dungeon.RED;
    }


    @Override
    public String toString() {
        return Dungeon.RED + this.getName() + " #" + _number + Dungeon.RESET;
    }
}
