package game;

import game.entities.Entity;
import game.items.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Positions {
    //? Class Postions tht will store the pos of entities, equipments and obstacles
    private HashMap<Entity, int[]> _entitiesPosition;
    private HashMap<Equipment, int[]> _equipmentPosition;
    private List<int[]> _obstacles;


    public Positions() {
        _entitiesPosition = new HashMap<>();
        _equipmentPosition = new HashMap<>();
        _obstacles = new ArrayList<>();
    }



    //? methods pour add entities et tt le tralala t'as compris
    public void addEntity(Entity entity, int[] position) {
        _entitiesPosition.put(entity, position);
    }
    public void addEquipment(Equipment equipment, int[] position) {
        _equipmentPosition.put(equipment, position);
    }
    public void addObstacle(int[] position) {
        _obstacles.add(position);
    }

    //? Getters
    public HashMap<Entity, int[]> getEntitiesPosition() {
        return _entitiesPosition;
    }
    public HashMap<Equipment, int[]> getEquipmentPosition() {
        return _equipmentPosition;
    }
    public List<int[]> getObstacles() {
        return _obstacles;
    }

}
