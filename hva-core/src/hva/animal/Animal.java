package hva.animal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import hva.habitat.Habitat;
import hva.specie.Specie;
import hva.vaccine.Vaccine;


public class Animal implements Serializable {
    private String _id;
    private String _name;
    private Specie _specie;
    private Habitat _habitat;
    private List<Vaccine> _vaccinations;
    private List<String> _health;

    public Animal(String id, String name, Specie specie, Habitat habitat) {
        _id = id;
        _name = name;
        _specie = specie;
        _habitat = habitat;
        _vaccinations = new ArrayList<>();
        _health= new ArrayList<>();
    }
        

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Specie getSpecie() {
        return _specie;
    }

    public Habitat getHabitat() {
        return _habitat;
    }

    public void setHabitat(Habitat habitat) {
        _habitat = habitat;
    }
    
    public void addVaccination(Vaccine vaccine) {
        _vaccinations.add(vaccine);
    }

    public void updateHealth(Vaccine vaccine, boolean success) {
        if (success) {
            _health.add("NORMAL");
        } else {
            int damage = calculateDamage(vaccine);
            if (damage == 0) {
                _health.add("CONFUSÃO");
            } else if (damage>=1 && damage<=4) {
                _health.add("ACIDENTE");
            } else if (damage>=5) {
                _health.add("ERRO");
            }
        }
    }

    public int calculateDamage(Vaccine vaccine) {
        int damage = 0;
        for (Specie applicableSpecie : vaccine.getApplicableSpecies()) {
            Set<Character> animalSpecieChars = new HashSet<>();
            for (char character1 : _specie.getName().toCharArray()) {
                animalSpecieChars.add(character1);
            }

            Set<Character> vaccineSpecieChars = new HashSet<>();
            for (char character2 : applicableSpecie.getName().toCharArray()) {
                vaccineSpecieChars.add(character2);
            }
            int size = Math.max(_specie.getName().length(), applicableSpecie.getName().length());
            animalSpecieChars.retainAll(vaccineSpecieChars);
            int common = animalSpecieChars.size();
            damage = Math.max(damage, size - common);
        }
        return damage;
    }


    public int calculateSatisfaction(){
        int equals = -1;
        int differents = 0;
        int area = _habitat.getArea();
        int influence = _habitat.getInfluence(_specie);
        for (Animal animal : _habitat.getAnimals()) {
            if (animal.getSpecie().equals(_specie)) {
                equals++;
            } else {
                differents++;
            }
        }
        return Math.round(20 + 3 * equals - 2 * differents + area / (1 + differents + equals) + influence);
    }

    @Override
    public String toString() {
        if (_health.isEmpty()) {
            return "ANIMAL|" + _id + "|" + _name + "|" + _specie.getId() + "|VOID|" + _habitat.getId();
        }
        return "ANIMAL|" + _id + "|" + _name + "|" + _specie.getId() + "|" + String.join(",", _health) + "|" + _habitat.getId();
    }

}