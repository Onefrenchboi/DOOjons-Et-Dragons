# Projet FAUCON-WAHL, Semaine 2

## Résumé

Ceci est le 2nd rapport de notre projet, il va faire l'inventaire de ce que nous avons fait cette semaine, ce qui est implémenté, ce qu'il reste à implémenter

## Structure du code

Notre code est toujours globalement structuré de la même façon que la semaine dernière, malgré quelques changements.

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
        ├──🧩GameEngine.java
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
            ├──🧩Utils.java
            ├──🧩Repo.java
        
├── uml/
    ├── Week2.puml

```
---
Par rapport à la semaine dernière, nous avons ajouté quelques classes, et modifié certaines autres.
Nous avons ajouté la classe `GameEngine`, qui va gérer le déroulement du jeu.

Nous avons également ajouté le package `utils`, qui va contenir des classes utilitaires.
* `Dice` a été renommé en `Utils`, et a été déplacé dans cedit package, car il ne servait globalement qu'a contenir la méthode `rollDice()`, qui va être utilisée dans plusieurs classes.
Ainsi, si, dans le futur, nous avons besoin de rajouter d'autres méthodes utilitaires, nous pourrons le faire sans problème.
* Repo a été déplacé dans le package `utils`, car il ne servait qu'à stocker des données, et n'avait pas d'autre rapport avec les équipements.


---

## Implémentation
### Ce qui est implémenté

* Tout ce qui à été fait la semaine dernière est toujours là, et a été légèrement modifié pour rentrer en accordance avec les nouvelles classes.
* La classe `Repo` a été remplie avec une méthode `initializeRepo()`, qui va remplir le repository avec les différentes armes et armures disponibles pour l'instant.
  * A noter que cette approche nous permet de rajouter facilement de nouvelles armes et armures, sans avoir à modifier le code de la classe `Repo`, mais juste à rajouter une ligne dans la méthode `initializeRepo()`.
* La classe `DM` a été remplie avec les méthodes `createX()`, qui vont chacune s'occuper de la création des differents éléments du donjon (Joueurs, monstres, items, obstacles) `SetDungeon()`, qui va venir mettre en place le donjon avec lesdits éléments, et `displayDungeon()`, qui va l'afficher.
* La classe `GameEngine` a été implémentée, et va utiliser les fonctions de `DM` pour créer le donjon, et l'afficher.
* Divers Getters et Setters ont été ajoutés dans les différentes classes.
* Plusieurs méthodes `toString()` ont été ajoutées, pour afficher les objets de manière plus lisible.

---

### Ce qu'il reste à implémenter AKA Toujours Ma TODO list du pauvre
* Le systeme du jeu en lui-même, i.e. le tour par tour, comment gérer les attaques, les déplacements, etc...


## Conclusion

Le projet arrive globalement à sa fin, tout du moins pour la phase 1.

Il ne reste que le système de jeu en tours par tours à implémenter, et quelques détails à régler.

Bien sûr, il reste encore sûrement des soucis auquel je n'ai pas pensé, mais notre code est bien organisé, et il est facile de rajouter des éléments. <small> => Je pense à la classe `Repo`, qui va nous permettre de rajouter facilement de nouvelles armes, les classes de personnages, etc... qui sont toutes faites par héritage. </small>

Comme je l'ai dit plus haut, il reste encore quelques détails à régler, mais je pense que nous avons fait un bon travail, et que nous avons bien avancé.

<small>Complétion du projet : 90%</small>

<small>Author : Mathéo F.</small>