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



    //? methods pour add et remove entities et tt le tralala t'as compris
    public void addEntity(Entity entity, int[] position) {
        _entitiesPosition.put(entity, position);
    }
    public void addEquipment(Equipment equipment, int[] position) {
        _equipmentPosition.put(equipment, position);
    }
    public void addObstacle(int[] position) {
        _obstacles.add(position);
    }
    public void removeEntity(Entity target) {
        _entitiesPosition.remove(target);
    }
    public void removeEquipment(Equipment target) {
        _equipmentPosition.remove(target);
    }



    /**
     * give the distance between an entity and a target position.
     * uses une formule dont je me souviens plus le nom mais qui permets de calculer la diag
     *
     * @param entity the entity to check the distance from
     * @param targetPosition the target position to check the distance to
     * @return the distance between the entity and the target position
     */
    public int distanceBetween(Entity entity, int[] targetPosition) {
        int[] entityPosition = _entitiesPosition.get(entity);

        int dx = Math.abs(entityPosition[0] - targetPosition[0]);
        int dy = Math.abs(entityPosition[1] - targetPosition[1]);

        return Math.max(dx, dy);
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
    public int[] getEntityPosition(Entity entity) {
        return _entitiesPosition.get(entity);
    }
}
