package Game.Items;

import java.util.List;

public class Repo {
    private List<Equipment> _repo;

    public Repo() {
        _repo = new java.util.ArrayList<>();
    }

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

    public List<Equipment> get_repo() {
       return _repo;
    }

//    public Equipment getEquipmentByName(String equipment) {
//        for (Equipment e : _repo) {
//            if (e.get_name().equals(equipment)) {
//                return e;
//            }
//        }
//        return null;
//    }

}
