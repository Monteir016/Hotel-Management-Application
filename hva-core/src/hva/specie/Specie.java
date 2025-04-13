package hva.specie;

import java.io.Serializable;

import java.util.List;

import hva.animal.Animal;

import java.util.ArrayList;

public class Specie implements Serializable {
    private String _id;
    private String _name;
    private List<Animal> _animals;

    public Specie(String id, String name) {
        _id = id;
        _name = name;
        _animals = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Specie getSpecie(String id) {
        return this;
    }

    public void addAnimal(Animal animal) {
        _animals.add(animal);
    }
}
