package game.entities;

import game.items.Weapon;
import game.utils.Dice;

public class Monster extends Entity {
    private int _number;
    private Weapon _weapon;





    public Monster(String species, int number, Weapon weapon) {
        super(species);
        this.setHp(Dice.roll(4, 4) + 3);
        this.setMaxHp(getHp());
        this._number = number;
        this._weapon = weapon;
        this.setPseudo(this.getName().length() >= 3 ? this.getName().substring(0, 2) + number : this.getName() + number);

    }



    @Override
    public String getColor() {
        return Dice.RED;
    }

    public String getInfo() {
        return "    HP ❤ : " + this.getHp() + "/" + this.getMaxHp() + "\n" +
                "   STR ✪ : " + this.getStats().getStrength() + "\n" +
                "   DEX ➔ : " + this.getStats().getDexterity() + "\n" +
                "   SPD ⚡ : " + this.getStats().getSpeed() + "\n";
    }

    @Override
    public String toString() {
        return Dice.RED + super.getName() + " #" + _number + Dice.RESET;
    }
}