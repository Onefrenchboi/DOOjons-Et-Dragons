# Projet FAUCON-WAHL, Semaine 2

## Résumé

Ceci est le 3ème rapport de notre projet, il va faire l'inventaire de ce que nous avons fait cette semaine, ce qui est implémenté, ce qu'il reste à implémenter

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
Par rapport à la semaine dernière, nous avons ajouté quelques classes, et modifié certaines autres.

**Nous avons supprimé la classe `GameEngine`**, et délégué son rôle à la classe `DM`, qui va s'occuper de la création du donjon, et de l'affichage de celui-ci, ainsi que la gestion de la partie (tour par tour, etc...).

**Nous avons ajoutés la classe `Positions`**, qui va gérer les positions des différents éléments du donjon (Joueurs, monstres, items, obstacles, etc...). Cette classe nous permet de centraliser le système des positions (comme son nom l'indique), afin d'avoir un code plus clair 

* `utils` a été renommé en `GameUtils`, pour éviter les confusions avec le package `utils` de Java.
* * La classe contient désormais des attributs statiques, qui vont nous permettre de gérer par exemple les couleurs des différents éléments du donjon. On y a également ajouté un scanner et un random, en static, pour l'entière application, afin de ne pas avoir à les créer à chaque fois que nous en avons besoin.
* La classe `Display` a été ajoutée, et va s'occuper de l'affichage de tout, à l'aide de plusieurs méthodes. Elle va par ailleurs utiliser les attributs statiques de `GameUtils` pour cela.

---

## Implémentation
### Ce qui est implémenté

* Tout ce qui à été fait la semaine dernière est toujours là, et a été légèrement modifié pour rentrer en accordance avec les nouvelles classes.
* Le jeu en sa globalité est implémenté, i.e. le donjon est créé, les joueurs et les monstres sont créés, et le donjon est affiché. La partie se lance, les joueurs peuvent bouger, attaquer, les tours se déroulent, la partie se finit lorsque que tous les joueurs sont morts, ou que tous les monstres sont morts.

---

### Ce qu'il reste à implémenter AKA Encore et Toujours Ma TODO list du pauvre
* Les sorts
* Quelques actions du DM et des joueurs sont à implémenter.
* Quelques petits problèmes d'affichage sont à régler.
* Test unitaires

## Conclusion

Le projet est preque terminé, il reste quelques petites choses à implémenter, mais le jeu est jouable, et fonctionne correctement. 


<small>Concrètement, si j'avais bossé un peu plus, j'aurais pu le finir cette semaine, mais je n'ai pas eu le temps de m'y consacrer autant que je l'aurais voulu.</small>

<small>Complétion du projet : 92.3%</small>

<small>Author : Mathéo F.</small>