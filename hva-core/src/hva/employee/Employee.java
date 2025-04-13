package hva.employee;

import java.io.Serializable;

import java.util.List;

import hva.habitat.Habitat;
import hva.tree.Tree;
import hva.Hotel;

import java.util.ArrayList;


public abstract class Employee implements Serializable {
    private final String _id;
    private final String _name;
    private List<String> _responsibilities;
    private Hotel _hotel;

    public Employee(String id, String name, List<String> responsibilities, Hotel hotel) {
        _id = id;
        _name = name;
        if (responsibilities == null) {
            _responsibilities = new ArrayList<>();
        } else {
            _responsibilities = new ArrayList<>(responsibilities);
        }
        _hotel = hotel;
    }


    public int calculateSatisfaction() {
        double satisfaction = 0;
        double work = 0;
        if (getType().equals("VET")) {
            for (String specieId : _responsibilities) {
                int population = _hotel.numberAnimalsBySpecie(specieId);
                int numVets = getSameResponsEmployees(specieId, "VET");
                work += population / numVets;
            }
            satisfaction = 20 - work;

        } else if (getType().equals("TRT")) {
            for (String habitatId : _responsibilities) {
                Habitat habitat = _hotel.getHabitat(habitatId);
                int population = habitat.numberAnimals();
                int area = habitat.getArea();
                int numTrts = getSameResponsEmployees(habitatId, "TRT");
                double effortTrees = 0;
                for (Tree tree : habitat.getTrees()) {
                    effortTrees += tree.getCleaningEffort();
                }
                double trabalhoNoHabitat = area + 3 * population + effortTrees;
                work += trabalhoNoHabitat / numTrts;
            }
            satisfaction = 300 - work;
        }

        return (int) Math.round(satisfaction);
    }

    public int getSameResponsEmployees(String responsibilityId, String type) {
        int numEmployees = 0;
        for (Employee employee : _hotel.getEmployees()) {
            if (employee.getType().equals(type) && employee.getResponsibilities().contains(responsibilityId)) {
                numEmployees++;
            }
        }
        return numEmployees;
    }

    public void addResponsibility(String responsibility) {
        _responsibilities.add(responsibility);
    }

    public void removeResponsibility(String responsibility) {
        _responsibilities.remove(responsibility);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public List<String> getResponsibilities() {
        return _responsibilities;
    }

    public List<String> filterResponsibilities() {
        List<String> filtered = new ArrayList<>();
        if (_responsibilities.isEmpty()) {
            return filtered;
        }
        String previous = _responsibilities.get(0);
        filtered.add(previous);
        for (int i = 1; i < _responsibilities.size(); i++) {
            String current = _responsibilities.get(i);
            if (!current.equals(previous)) {
                filtered.add(current);
                previous = current;
            }
        }
        return filtered;
    }

    public abstract String getType();

    @Override
    public String toString() {
        String responsibilities = String.join(",", filterResponsibilities());
        return responsibilities.isEmpty() ?
            String.format("%s|%s|%s", getType(), _id, _name) :
            String.format("%s|%s|%s|%s", getType(), _id, _name, responsibilities);
    }
}
