package game;

import game.entities.*;

public class GameEngine {
    private boolean _gameState;
    private DM _dungeonMaster;
    private int _turn;
    private Entity _currentEntity;



    public GameEngine() {
        _gameState = true;
        _dungeonMaster = new DM();
        _turn = 1;


    }

    public void createGame() {
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        System.out.println("----Préparation de la partie----");

        _dungeonMaster.createCharacters();
        _dungeonMaster.createMonsters();
        _dungeonMaster.createEquipments();
        _dungeonMaster.createDungeon();

        _dungeonMaster.setDungeon();

        System.out.println("----Début de la partie----");
    }

    public void showInfo(){
        System.out.println("********************************************************************************");
        //TODO : implement dungeon number
        System.out.println("Dungeon : " );
        _currentEntity = _dungeonMaster.getEntitiesSortedByInitiative().getFirst();
        System.out.println(_currentEntity.toString());
        System.out.println("********************************************************************************");


        System.out.println("Turn " + _turn + " : ");
        for (Entity entity : _dungeonMaster.getEntitiesSortedByInitiative()) {
            String prefix = (entity == _currentEntity) ? "-> " : "   ";
            System.out.println(prefix + entity.getPseudo() + "   " + entity.toString() + " (" + entity.getHp() + "/" + entity.getMaxHp() + ")");
        }
        _dungeonMaster.showBoard();

    }

    public void runGame() {
        while (_gameState) {
            for (Entity entity : _dungeonMaster.getEntitiesSortedByInitiative()) {

            }
        }
    }
}
