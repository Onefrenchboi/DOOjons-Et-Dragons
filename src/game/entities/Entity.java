package game.entities;


import game.items.Armor;
import game.items.Equipment;
import game.items.Weapon;
import game.utils.GameUtils;

import java.util.List;

public abstract class Entity {
    private String _name;
    private String _pseudo;
    private Statistics _stats;
    private Weapon _equippedWeapon;
    private Armor _equippedArmor;
    private int _maxHp;


    protected Entity(String name) {
        this._name = name;
        this._stats = new Statistics(
                0,
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3));
    }

    public Statistics getStats() {
        return _stats;
    }



    //!popopo le instanceof du pauvre
    public boolean isPlayer() {
        return false; // par defaut, les entites ne sont pas des joueurs
    }
    public boolean isMonster() {
        return false; // par defaut, les entites ne sont pas des monstres
    }




    public abstract String toString(); //juste la pour etre overidden donc abstract

    public String getName() {
        return _name;
    }

    public String getPseudo() {
        return _pseudo;
    }


    public abstract String getInfo();


    public int getInitiative() {
        return _stats.getInitiative();
    }

    public void removeHp(int hp) {
        this._stats.addStatistics(new Statistics(-hp, 0, 0, 0, 0));
    }
    public void setHp(int hp) {
        this._stats.addStatistics(new Statistics(hp, 0, 0, 0, 0));
    }

    public void setMaxHp(int maxHp) {
        this._maxHp = maxHp;
    }
    public int getHp() {
        return _stats.getHp();
    }

    public int getMaxHp() {
        return _maxHp;
    }

    public void setPseudo(String pseudo) {
        this._pseudo = pseudo;
    }

    public String getColor() {
        return GameUtils.PURPLE;
    }

    public boolean isAlive() {
        if (_stats.getHp() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canAttack(Entity target) {
        if ((this.isMonster() && target.isPlayer() || this.isPlayer() && target.isMonster()) && this.getEquippedWeapon() != null) {
            return true;
        }
        return false;
    }


    public int getAC() {
        if (_equippedArmor == null) {
            return 0;
        }
        return _equippedArmor.getAC();
    }

    public abstract String displayInventory();
    public abstract List<Equipment> getInventory();

    public Weapon getEquippedWeapon() {
        return _equippedWeapon;
    }
    public Armor getEquippedArmor() {
        return _equippedArmor;
    }



    protected void setEquippedWeapon(Weapon weapon) {
        this._equippedWeapon=weapon;
    }
    protected void setEquippedArmor(Armor armor){
        this._equippedArmor=armor;
    }

    public abstract void equipArmor(Equipment equipment);

    public abstract void equipWeapon(Equipment equipment);
}
