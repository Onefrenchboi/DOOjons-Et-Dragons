# Projet FAUCON-WAHL, Semaine 2

## Résumé

Ceci est le 4ème rapport de notre projet. Nous nous sommes concentrés sur d'autres projets cette semaine, il est donc moins garni que les précédents.
## Structure du code

La structure de notre code a grandement évolué depuis la semaine dernière. En voici un aperçu :
##### Légende

📁 => Vous pouvez deviner je pense

🧩 => Class

🧬 => Abstract Class

##### Nous avons décidé de nommer nos classes et packages en anglais.

```bash

├── README.md
├── .gitignore
├──📁src/
    ├── Main.java
    ├──📁game/
        ├──🧩Dm.java
        ├──🧩Dungeon.java
        ├──🧩Positions.java
        ├──📁Entities/
            ├──📁Classes/
                ├──🧬CharacterClass.java
                ├──🧩Cleric.java
                ├──🧩Rogue.java
                ├──🧩Warrior.java
                ├──🧩Wizard.java
            ├──📁Races/
                ├──🧬Race.java
                ├──🧩Dwarf.java
                ├──🧩Halfling.java
                ├──🧩Human.java
                ├──🧩Elf.java
            ├──🧩Character.java
            ├──🧩Monster.java
            ├──🧩Statistics.java
        ├──📁Equipment/
            ├──🧬Equipment.java
            ├──🧬Armor.java
            ├──🧬Weapon.java
            ├──🧩Repo.java
            ├──🧩HeavyArmor.java
            ├──🧩LightArmor.java
            ├──🧩MeleeWeapon.java
            ├──🧩WarMeleeWeapon.java
            ├──🧩RangedWeapon.java
        ├──📁utils/
            ├──🧩GameUtils.java
            ├──🧩Repo.java
            ├──🧩Display.java
        
├── uml/
    ├── Week3.puml

```
---
Par rapport à la semaine dernière, notre structure de code n'a pas changé.

---

## Implémentation
### Ce qui est implémenté

* Tout ce qui à été fait la semaine dernière est toujours là.
* Le fait d'équiper un personnage avec une arme ou une armure avant le donjon.
---

### Ce qu'il reste à implémenter AKA Devinez quoi ? C'est Toujours et Encore et Toujours Ma TODO list du pauvre
* Les sorts (non-implémenté pour l'instant, mais la logique est déjà préparée dans nos têtes.)
* Quelques actions du DM et des joueurs sont à implémenter.
* Test unitaires

## Conclusion

Le projet n'a pas beaucoup avancé cette semaine, mais nous avons pu nous concentrer sur d'autres projets. Le projet sera terminé dans les jours qui suivent, probablement même demain.

<small>Complétion du projet : 95.21%</small>

<small>Author : Mathéo F.</small>