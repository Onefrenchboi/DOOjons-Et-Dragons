# Projet FAUCON-WAHL, Semaine 5

## Résumé

Ceci est le 5ème et dernier rapport de notre projet. Pour cette raison, il sera beaucoup plus développé que les précédents.

## Structure du code

Voici la structure finale de notre code
##### Légende

📁 => Package

🧩 => Class

🧬 => Abstract Class

📚 => Enums



##### Nous avons décidé de nommer nos classes et packages en anglais.

```bash

├── README.md
├── .gitignore
├──📁src/
    ├── Main.java
    ├──📁game/
        ├──🧩Dm.java
        ├──🧩Dungeon.java
        ├──📁entities/
            ├──📁classes/
                ├──🧬CharacterClass.java
                ├──🧩Cleric.java
                ├──🧩Rogue.java
                ├──🧩Warrior.java
                ├──🧩Wizard.java
            ├──📁races/
                ├──🧬Race.java
                ├──🧩Dwarf.java
                ├──🧩Halfling.java
                ├──🧩Human.java
                ├──🧩Elf.java
            ├──🧩Character.java
            ├──🧩Monster.java
            ├──📚EntityType.java
            ├──🧩Statistics.java
        ├──📁items/
            ├──📁equipments/
                ├──📁Equipment/
                    ├──🧩HeavyArmor.java
                    ├──🧩LightArmor.java
                    ├──🧩MeleeWeapon.java
                    ├──🧩WarMeleeWeapon.java
                    ├──🧩RangedWeapon.java
            ├──🧬Equipment.java
            ├──🧬Armor.java
            ├──🧬Weapon.java
            ├──📚EquipmentType.java
        ├──📁utils/
            ├──🧩GameUtils.java
            ├──📚EquipmentRepository.java
            ├──🧩Display.java
        ├──📁spells/
            ├──🧬Spell.java
            ├──🧩Heal.java
            ├──🧩MagicWeapon.java
            ├──🧩BoogieWoogie.java
        
├── uml/
    ├── Week5.puml

```
---
# Le Projet
## Les classes
### Gestion du jeu
* <b> Le Jeu est géré par la classe `Dm`, qui est le maître du jeu. Il gère les personnages, les monstres, les objets et le déroulement de la partie.
* La classe `Dungeon` représente le donjon dans lequel se déroule le jeu.</b>


### Entités du jeu
* <b>Les entités du jeu sont contenues dans le package `entities`. </b>
  * Une entité est un personnage (`Character`) ou un monstre (`Monster`). Elle possède un type (`EntityType`), un nom.
  * Une entité possède des statistiques (`Statistics`) qui sont gérées par la classe `Statistics`.
  * Un personnage est une entité qui possède une classe (`CharacterClass`) et une race (`Race`).
  * Les classes de personnages sont contenues dans le package `classes` et les races dans le package `races`.
* Les entités peuvent avoir des objets équipés, qui sont contenus dans le package `items`.

### Objets du jeu
* <b>Les objets du jeu sont contenus dans le package `items`. </b>
  * Un objet est un équipement (`Equipment`). Un équipement possède un type (`EquipmentType`), qui est soit "Armor", soit "Weapon".
  * Les armes et les armures sont des équipements, mais elles ont des caractéristiques spécifiques (dégâts pour une arme, classe d'armure pour une armure).
  * Les différents équipements sont contenus dans le package `equipments`.
    * Les armes peuvent être des `MeleeWeapon`, `WarMeleeWeapon` ou `RangedWeapon`.
    * Les armures peuvent être des `HeavyArmor` ou `LightArmor`.

### Sorts du jeu
* <b>Les sorts sont contenus dans le package `spells`.</b> 
  * Un sort est une action magique qui peut être lancée par un personnage.
  * Les sorts sont des classes qui héritent de la classe abstraite `Spell`.
  * Les sorts peuvent avoir des effets différents, comme soigner (`Heal`), améliorer une arme (`MagicWeapon`) ou bien échanger deux entités de place (`BoogieWoogie`).
  * Les sorts sont lancés par les personnages qui ont la classe `Wizard` ou `Cleric`.
  
### Utilitaires du jeu
*<b> Les utilitaires du jeu sont contenus dans le package `utils`. </b>
  * La classe `GameUtils` contient des méthodes utilitaires pour le jeu, comme la génération de nombres aléatoires, la gestion des inputs de l'user, etc...
  * La classe `Display` contient des méthodes pour afficher des messages à l'écran.
  * L'enum `EquipmentRepository` est un dépôt d'équipements qui permet de gérer les équipements disponibles dans le jeu.
    * <small>(Note : d'après ce que j'ai compris, j'ai à moitié reverse engineer un factory pattern, selon A.K. et je trouve ça vachement drôle.)</small>


## Déroulement du jeu

Le jeu se déroule de la manière suivante :
1. <b>On instancie un maître du jeu (DM) dans le main, et on lance la methode `createGame()`</b>
   1. S'en suit la création des personnages, qui sont demandés à l'utilisateur (Ici, ceux qui vont jouer les persos).
   2. S'en suit la création des monstres, équipements, et obstacles, qui sont également demandés à l'utilisateur. (Ici, celui/celle qui va jouer le.a DM)
   3. S'en suit la création du donjon, puis la mise en place des pièces du jeu dans le donjon.
2. <b>On lance la méthode `startGame()` du DM, qui va lancer le jeu.</b>
   1. Dans l'ordre de l'initiative, les différentes entités vont pouvoir jouer (attaquer, bouger, s'équiper/ramasser des items/lancer des sorts pour les personnages)
   2. Après chaque action, le DM peut intervenir, pour faire plusieurs actions.
   3. Si un monstre est tué, il est retiré du jeu.
   4. Si un personnage est tué, le jeu est perdu.
   5. Si tous les monstres sont tués, on passe au prochain donjon.
3. <b>Une fois tous les montres tués, on passe à la méthode `nextDungeon()`, qui va permettre de passer au prochain donjon.</b>
   1. On recommence le processus de création de donjon, mais cette fois-ci, les personnages sont déjà créés.
4. <b>Le jeu continue jusqu'à ce que les 3 donjons soient terminés (ou qu'un personnage meurt).</b>


## Points forts de notre implémentation

* <b>Le code est bien structuré, avec des packages et des classes bien définies.</b>
* <b>Nous avons tenté de respecter les principes SOLID, en particulier le principe de responsabilité unique.</b>
  * Classe `Dm` : responsable de la gestion du jeu, et la création des personnages, des monstres et des objets.
  * Classe `Dungeon` : responsable de la gestion du donjon et des positions des entités.
  * Classe `Display` : responsable de l'affichage des messages à l'écran.
  * Etc...
* <b>Notre code est facilement extensible, grâce à l'utilisation de classes abstraites et d'énumérations.</b>
  * Classes abstraites pour les entités, les équipements et les sorts. => On peut facilement ajouter de nouvelles entités, équipements ou sorts en créant de nouvelles classes qui héritent des classes abstraites.
  * Enums pour les types d'entités, d'équipements => On peut facilement ajouter de nouveaux types d'entités et d'équipements.
  * Enum pour les résultats des actions (par exemple, si une attaque touche ou non). => On peut facilement ajouter de nouveaux résultats d'actions, et donc des nouvelles actions.
* <b>Notre code est commenté.</b>


## Possibles points faibles de notre implémentation
* <b>Réécriture de certaines classes peut-être nécessaire</b>
  * Certaines de nos classes, notamment celles créees en première semaine, sont peut-être un peu trop maladroites, et pourraient être réécrites pour être plus claires.
* <b>Lèger manque de relecture</b>
  * Certaines parties du code pourraient être mieux écrites, mais nous avons manqué de temps pour les relire.
* <b>Pas de tests unitaires</b>
* <b><small>Notre Uml est trop grand et prend trop longtemps à charger :(</small></b>

## Conclusion

Le projet est complété. Nous avons réussi à implémenter toutes les fonctionnalités du sujet, et nous sommes satisfaits du résultat final.

De plus, grâce à ce projet, nous avons pu apprendre beaucoup de choses sur la programmation orientée objet, l'utilisation de Git (même si savions déjà l'utiliser), et plus encore.

Le code est structuré du mieux que nous avons pu, et, comme dit plus haut, nous avons tenté de respecter les principes SOLID, même si nous n'avons peut-être pas réussi sur tous les fronts.


Sur une note un peu plus personnelle, j'ai beaucoup aimé travailler sur ce projet, et je suis fier du résultat final. Le sujet était intéressant, ce qui m'a permis de me plonger dedans et de m'investir.

<small>Complétion du projet : ✨100%✨</small>

<small>Author : Mathéo F.</small>