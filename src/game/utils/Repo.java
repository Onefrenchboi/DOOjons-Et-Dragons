package game.utils;

import game.items.*;

import java.util.List;

public class Repo {
    private static List<Equipment> _repo;

    public Repo() {
        _repo = new java.util.ArrayList<>();
        //light Armors
        addEquipment(new LightArmor("Scale armor", 9));
        addEquipment(new LightArmor("Half-plate", 10));

        //heavy Armors
        addEquipment(new HeavyArmor("Chainmail", 11));
        addEquipment(new HeavyArmor("Plate Armor", 12));

        //common Melee Weapons
        addEquipment(new MeleeWeapon("Stick", 1, 1,6));
        addEquipment(new MeleeWeapon("Mace", 1, 1,6));

        //war Melee Weapons
        addEquipment(new WarMeleeWeapon("Longsword", 1, 1,8));
        addEquipment(new WarMeleeWeapon("Rapier", 1,1, 8));
        addEquipment(new WarMeleeWeapon("Two-handed sword", 2,2, 6));

        //ranged Weapons
        addEquipment(new RangedWeapon("Light crossbow", 16, 1,8));
        addEquipment(new RangedWeapon("Sling", 6, 1,4));
        addEquipment(new RangedWeapon("Shortbow", 16, 1,6));
    }


    //? Add and remove equipment methods
    public void addEquipment(Equipment e) {
        if (e != null && !_repo.contains(e)) {
            _repo.add(e);
        }
    }
    public void removeEquipment(Equipment e) {
        if (e != null && _repo.contains(e)) {
            _repo.remove(e);
        } else {
            System.out.println("Existe pas.");
        }
    }


    //? Utility methods
    public static Equipment getEquipmentByName(String equipment) {
        for (Equipment e : _repo) {
            if (e.getName().equals(equipment)) {
                return e;
            }
        }
        return null;
    }

    //? Getters
    public List<Equipment> getEquipments() {
       return _repo;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Repo contains:\n");
        for (Equipment e : _repo) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}
