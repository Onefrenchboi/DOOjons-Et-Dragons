package game.utils;

import game.items.*;
import game.items.equipments.*;
import java.util.Arrays;
import java.util.List;

public enum EquipmentRepository {
    //light Armor
    SCALE_ARMOR(new LightArmor("Scale armor", 9)),
    HALF_PLATE(new LightArmor("Half-plate", 10)),

    //heavy Armor
    CHAINMAIL(new HeavyArmor("Chainmail", 11)),
    PLATE_ARMOR(new HeavyArmor("Plate Armor", 12)),

    //melee Weapons
    STICK(new MeleeWeapon("Stick", 1, 1, 6)),
    MACE(new MeleeWeapon("Mace", 1, 1, 6)),

    //<ar Melee Weapons
    LONGSWORD(new WarMeleeWeapon("Longsword", 1, 1, 8)),
    RAPIER(new WarMeleeWeapon("Rapier", 1, 1, 8)),
    TWO_HANDED_SWORD(new WarMeleeWeapon("Two-handed sword", 2, 2, 6)),

    //ranged Weapons
    LIGHT_CROSSBOW(new RangedWeapon("Light crossbow", 16, 1, 8)),
    SLING(new RangedWeapon("Sling", 6, 1, 4)),
    SHORTBOW(new RangedWeapon("Shortbow", 16, 1, 6));

    private final Equipment _equipment;

    EquipmentRepository (Equipment equipment) {
        this._equipment = equipment;
    }

    public Equipment get() {
        return _equipment;
    }

    //? list of all equipment
    public static List<Equipment> getAllEquipment() {
        return Arrays.asList(EquipmentRepository.values())
                     .stream()
                     .map(EquipmentRepository::get)
                     .toList();
    }
}
