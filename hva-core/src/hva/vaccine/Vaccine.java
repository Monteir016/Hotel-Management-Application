package hva.vaccine;

import hva.animal.Animal;
import hva.specie.Specie;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Vaccine implements Serializable {
    private String _id;
    private String _name;
    private List<Specie> _applicableSpecies;
    private List<Animal> _vaccinatedAnimals;

    public Vaccine(String id, String name, List<Specie> specie) {
        _id = id;
        _name = name;
        _vaccinatedAnimals = new ArrayList<>();
        _applicableSpecies = (specie != null) ? specie : null;
    }

    public boolean isApplicable(Specie specie) {
        return _applicableSpecies.contains(specie);
    }

    public void addApplicableSpecie(Specie specie) {
        _applicableSpecies.add(specie);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public List<Specie> getApplicableSpecies() {
        return _applicableSpecies;
    }

    public boolean addVaccinatedAnimal(Animal animal, String vetId) {
        _vaccinatedAnimals.add(animal);        
        if (!_applicableSpecies.contains(animal.getSpecie())) {
            animal.updateHealth(this, false);
            return false;
        }
        animal.updateHealth(this, true);
        return true;
    }

    @Override
    public String toString() {  
        StringBuilder speciesIds = new StringBuilder();
        if (_applicableSpecies.isEmpty() || _applicableSpecies == null) {
            return "VACINA|" + _id + "|" + _name + "|" + _applicableSpecies.size();
        }
        _applicableSpecies.stream()
        .map(Specie::getId).sorted(String::compareToIgnoreCase)
        .forEach(id -> {
            if (speciesIds.length() > 0) {
                speciesIds.append(",");
            }
            speciesIds.append(id);
        });
        return "VACINA|" + _id + "|" + _name + "|" + _vaccinatedAnimals.size() + "|" + speciesIds.toString();
    }
}