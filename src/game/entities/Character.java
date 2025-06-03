package game.entities;
import game.entities.races.*;
import game.entities.classes.*;
import game.items.*;
import game.spells.*;
import game.utils.GameUtils;



import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {
    private Race _race;
    private CharacterClass _class;
    private List<Equipment> _inventory;
    private List<Spell> _spells;


    public Character(String name, Race race, CharacterClass charclass) {
        super(name, EntityType.PLAYER);
        this._race = race;
        this._class = charclass;
        this._inventory = new ArrayList<>(charclass.getStartingEquipment());
        this._spells = new ArrayList<>();

        this.getStats().addStatistics(_race.getBonusStats());
        this.getStats().addStatistics(_class.getBonusStats());

        //? Handling du pseudo pour garder l'alignement de la map
        if (name.length() == 1) {
            this.setPseudo(" " + name + " ");
        } else if (name.length() == 2) {
            this.setPseudo(name + " ");
        } else {
            this.setPseudo(name.substring(0, 3));
        }

        this.setMaxHp(this.getHp());

        if (isCleric()) {
            _spells.add(new Heal());
        } else if (isWizard()) {
            _spells.add(new Heal());
            _spells.add(new BoogieWoogie());
            _spells.add(new MagicWeapon());
        }

    }




    //? Eviter les Instanceof
    //! on fait que isWizard et isCleric pour l'instant (pour les sorts), mais on peut en ajouter d'autres si besoin
    @Override
    public boolean isPlayer() {
        return true;
    }
    public boolean isCleric() {
        return _class.getName().equalsIgnoreCase("Cleric");
    }
    public boolean isWizard() {
        return _class.getName().equalsIgnoreCase("Wizard");
    }


    //? Inventory management
    public String displayInventory() {
        if (_inventory == null || _inventory.isEmpty()) {
            return "Inventory is empty.";
        }
        StringBuilder inventoryDisplay = new StringBuilder();
        for (Equipment equipment : _inventory) {
            int n = _inventory.indexOf(equipment);
            inventoryDisplay.append("[" + n + "] ").append(equipment.toString()).append(" | ");
        }
        return inventoryDisplay.toString();
    }
    public void addToInventory(Equipment equipment) {
        _inventory.add(equipment);
    }



    /**
     * Eqips the specified weapon/armor for the character.
     * Applies the bonus stats from the equipment to the character's stats.
     * also removes the equipment from the inventory. (or adds it if reequipping)
     * @param equipment the equipment item to equip (either a Weapon or Armor).
     */
    @Override
    public void equipWeapon(Equipment equipment){
        if (this.getEquippedWeapon()==null){
            this.setEquippedWeapon((Weapon) equipment);
            this.getStats().addStatistics(equipment.getBonusStats());
            this._inventory.remove(equipment);
        }
        else {
            this.getStats().removeStatistics(this.getEquippedWeapon().getBonusStats()); //remove stats de l'arme d'avant
            this.getStats().addStatistics(equipment.getBonusStats()); //add les stats de la nouvelle arme
            this ._inventory.add(this.getEquippedWeapon());
            this.setEquippedWeapon((Weapon) equipment);
            this._inventory.remove(equipment);
        }
    }
    @Override
    public void equipArmor(Equipment equipment){
        if (this.getEquippedArmor()==null){
            this.setEquippedArmor((Armor) equipment);
            this.getStats().addStatistics(equipment.getBonusStats());
            this._inventory.remove(equipment);
        }
        else {
            this.getStats().removeStatistics(this.getEquippedWeapon().getBonusStats()); //remove stats de l'arme d'avant
            this.getStats().addStatistics(equipment.getBonusStats()); //add les stats de la nouvelle arme
            this ._inventory.add(this.getEquippedArmor());
            this.setEquippedArmor((Armor) equipment);
            this._inventory.remove(equipment);
        }
    }


    //? Getters
    public Spell getSpellByName(String name) {
        for (Spell spell : _spells) {
            if (spell.getName().equalsIgnoreCase(name)) {
                return spell;
            }
        }
        return null;
    }
    public String getInfo(){
        return  "   HP ❤ : " + this.getHp() + "/" + this.getMaxHp() + "\n" +
                "   Armor ⊙ : " + (this.getEquippedArmor() != null ? this.getEquippedArmor().getName() : "None") + "\n" +
                "   Weapon ⚔ : " + (this.getEquippedWeapon() != null ? this.getEquippedWeapon().toString() : "None") + "\n" +
                "   Inventory ◼ : " + this.displayInventory() + "\n" +
                "   STR ✪ : " + this.getStats().getStrength() + "\n" +
                "   DEX ➔ : " + this.getStats().getDexterity() + "\n" +
                "   SPD ⚡ : " + this.getStats().getSpeed() + " (You can move " + this.getStats().getSpeed()/3 + " spaces per turn).";
    }
    public List<Equipment> getInventory() {
        return _inventory;
    }


    @Override
    public String toString() {
        return GameUtils.PURPLE + super.getName() + " the " + _class.getName() + " " + _race.getName() + GameUtils.RESET;
    }
}
