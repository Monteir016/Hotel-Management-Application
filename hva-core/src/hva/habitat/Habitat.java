package hva.habitat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import hva.animal.Animal;
import hva.tree.Tree;
import hva.specie.Specie;

public class Habitat implements Serializable{
    private String _id;
    private String _name;
    private int _area;
    private List<Animal> _animals;
    private List<Tree> _trees;
    private Map<Specie, Influence> _influences; 

    public Habitat(String id, String name, int area, List<Tree> treeIDs) {
        _id = id;
        _name = name;
        _area = area;
        _animals = new ArrayList<>();
        _trees = (treeIDs != null) ? treeIDs : null;
        _influences = new HashMap<>();
    }

    public void changeInfluence(Specie specie, Influence influence) {
        _influences.put(specie, influence);
    }

    public int getInfluence(Specie specie) {
        String inf = _influences.getOrDefault(specie, Influence.NEU).toString();
        if (inf.equals("POS")) {
            return 20;
        } else if (inf.equals("NEG")) {
            return -20;
        } else {
            return 0;
        } 
    }

    public String getId() {
        return _id;
    }

    public List<Animal> getAnimals() {
        return _animals;
    }

    public String getName() {
        return _name;
    }

    public int getArea() {
        return _area;
    }

    public void addAnimal(Animal animal) {
        _animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        _animals.remove(animal);
    }

    public void addTree(Tree tree) {
        _trees.add(tree);
    }

    public void changeArea(int area) {
        _area = area;
    }

    public List<Tree> getTrees() {
        return _trees;
    }

    public int numberAnimals() {
        return _animals.size();
    }
    
    @Override
    public String toString() {
        return "HABITAT|" + _id + "|" + _name + "|" + _area + "|" + _trees.size();
    }

}
