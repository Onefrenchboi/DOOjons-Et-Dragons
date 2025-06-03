package game.entities;


import game.items.*;
import game.items.enums.EquipmentType;
import game.utils.GameUtils;

import java.util.List;

public abstract class Entity {
    private String _name;
    private String _pseudo;
    private Statistics _stats;
    private Weapon _equippedWeapon;
    private Armor _equippedArmor;
    private int _maxHp;
    private EntityType _typeEntity;


    protected Entity(String name, EntityType type) {
        this._name = name;
        this._typeEntity = type;
        this._stats = new Statistics(
                0,
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3),
                (GameUtils.roll(4, 4) + 3));
    }




    //? je sais pas ou la mettre elle :)
    public void removeHp(int hp) {
        this._stats.addStatistics(new Statistics(-hp, 0, 0, 0, 0));
    }



    //? le instanceof du pauvre
    public boolean isPlayer() {
        return false; // par defaut, les entites ne sont pas des joueurs
    }
    public boolean isMonster() {
        return false; // par defaut, les entites ne sont pas des monstres
    }

    /**
     * Checks if the entity is alive based on its HP.
     *
     * @return true if the entity's HP is greater than 0, false sinon
     */
    public boolean isAlive() {
        if (_stats.getHp() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Checks if the entity can attack a target based on their types and equipped weapon.
     * methods checks if the entity is a player or a monster and if the target is of the opposite type.
     * and if the entity has an equipped weapon.
     * @param target the target entity to check against
     * @return true if the entity can attack the target, false otherwise
     */
    public boolean canAttack(Entity target) {
        if ((this.isMonster() && target.isPlayer() || this.isPlayer() && target.isMonster()) && this.getEquippedWeapon() != null) {
            return true;
        }
        return false;
    }

    /**
     * Equip either an armor or a weapon to the entity.
     *
     * @param equipment the equipment to equip, which can be either an Armor or a Weapon
     */
    public abstract void equipArmor(Equipment equipment);
    public abstract void equipWeapon(Equipment equipment);





    /**
     * Displays the inv
     *
     *
     * @return a string representation of the entity's inventory
     */
    public abstract String displayInventory();



    //? Setters
    protected void setEquippedWeapon(Weapon weapon) {
        this._equippedWeapon=weapon;
    }
    protected void setEquippedArmor(Armor armor){
        this._equippedArmor=armor;
    }
    public void setHp(int hp) {
        this._stats.addStatistics(new Statistics(hp, 0, 0, 0, 0));
    }
    public void setMaxHp(int maxHp) {
        this._maxHp = maxHp;
    }
    public void setPseudo(String pseudo) {
        this._pseudo = pseudo;
    }


    //? Getters
    public Statistics getStats() {
        return _stats;
    }
    public Weapon getEquippedWeapon() {
        return _equippedWeapon;
    }
    public Armor getEquippedArmor() {
        return _equippedArmor;
    }
    public String getColor() {
        return GameUtils.PURPLE;
    }
    public int getHp() {
        return _stats.getHp();
    }
    public int getMaxHp() {
        return _maxHp;
    }
    public int getAC() {
        if (_equippedArmor == null) {
            return 0;
        }
        return _equippedArmor.getArmorClass();
    }
    public String getName() {
        return _name;
    }
    public String getPseudo() {
        return _pseudo;
    }
    public int getInitiative() {
        return _stats.getInitiative();
    }
    public EntityType getType() {
        return _typeEntity;
    }


    //? getters abstraits pour les sous-classes
    public abstract String getInfo();
    public abstract List<Equipment> getInventory();


    public abstract String toString(); //juste la pour etre overidden donc abstract



}
