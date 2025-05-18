# Projet FAUCON-WAHL

## Résumé

Ceci est un rapport qui va faire l'inventaire de ce que nous avons fait, ce qui est implémenté, ce qu'il reste à implémenté, et toute autres choses que j'oublie très certainement de noter dans ce résumé.


## Structure du code

Notre code est structuré de cette façon :

##### Légende

📁 => Vous pouvez deviner je pense

🧩 => Class

🧬 => Abstract Class

##### Nous avons décidé de nommer nos classes et packages en anglais.

```bash
.
├── README.md
├── .gitignore
├──📁src/
    ├── Main.java
    ├──📁game/
        ├──🧩Dm.java
        ├──🧩Dice.java
        ├──🧩Dungeon.java
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
        
├── uml/
    ├── Week1.puml

```
---
Nous avons donc un package **game** qui contient l'intégralité du projet, dans lequel nous retrouvons tout d'abord les classes *DM*, *Dice*, et *Dungeon*, puis plusieurs packages différents contenant toutes nos classes. 

Le package **Entities** contient :
* Le package **Classes**, contenant quatre classes héritant d'une classe abstraite *CharacterClass*
* Le package **Races**, Idem mais avec les races héritant d'une classe abstraite *Race*
* Les classes *Character, Monster, et Statistics*

Le package **Equipment** contient :

* La classe abstraite *Equipement*, dont hérite *Armor* et *Weapon*, dont héritent les differents types d'armes et armures.
## Implémentation
### Ce qui est implémenté

* La génération du Donjon avec obstacles (sans monstres ni joueurs), ainsi que son affichage
* Le système de statistiques d'un personnage (classe *Statistics*).
* L'entièreté des *Classes* et *Races*, i.e. les bonus qu'elles apportent.
* L'entièreté des classes du package **Equipement**, càd :
    
    * Le système des armes ayant une portée, un dé de dégâts, des bonus/malus selon leur type.
    * Le système des armures ayant une CA, des malus/bonus selon leur type.

---

### Ce qu'il reste à implémenter AKA Ma TODO list du pauvre
* Le joueur en lui-même
* Les monstres
* La logique du jeu (Tour/Tour, où stocker les données et/ou méthodes nécessaires, comment le finir, etc...)
* Les dés <small>(oui je sais, j'aurais dû le faire en premier, mais j'avoue avoir totalement oublié)</small>
* Refaire toutes les méthodes ToString() <small>(j'ai totalement zappé aussi :o)</small>
* Quelques problèmes de logique à réparer.
* Créer des tests.


## Conclusion

Le projet est très bien entamé. <small>(j'oserai même poser le taux de complétion à environ 60%)</small>

Le plus dur, selon moi, est passé (trouver la logique des classes, de comment structurer le tout, etc...), mais l'implémentation du déroulement du jeu reste une tâche quelque peu terrifiante, quoique abordable. 

<small>Author : Mathéo F.</small>