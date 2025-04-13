package hva.tree;

import java.io.Serializable;

import hva.Hotel;


public abstract class Tree implements Serializable{
    private final String _id;
    private final String _name;
    private int _age;
    private int _cleaningDifficulty;
    private Hotel _hotel;
    private Float _yearOfCreation;

    public Tree(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        _id = id;
        _name = name;
        _age = age;
        _cleaningDifficulty = cleaningDifficulty;
        _hotel = hotel;
        _yearOfCreation = hotel.getCurrentYear();
    }

    public void ageTree() {
        _age++;
    }  

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getAge() {
        return _age;
    }

    public Tree getTree(String id) {
        if (this._id.equals(id)) {
            return this;
        }
        return null;
    }

    public double getCleaningDifficulty() {
        return _cleaningDifficulty;
    }

    private int getSeasonalEffort(String season) {
        switch (season) {
            case "inverno":
                return getType().equals("CADUCA") ? 0 : 2;
            case "primavera":
                return 1;
            case "verao":
                return getType().equals("CADUCA") ? 2 : 1;
            case "outono":
                return getType().equals("CADUCA") ? 5 : 1;
            default:
                return 1;
        }
    }

    public double getCleaningEffort() {
        float currentYear = _hotel.getCurrentYear();
        float age = currentYear - _yearOfCreation + _age;
        String currentSeason = _hotel.getCurrentSeason();
        int seasonalEffort = getSeasonalEffort(currentSeason);
        double ageFactor = Math.log((int)age + 1);
        return _cleaningDifficulty * seasonalEffort * ageFactor;
    }


    public abstract String getType();

    public abstract String Cicle(String season);

    @Override
    public String toString() {
        int age = (int) (_hotel.getCurrentYear() - _yearOfCreation + _age);
        return "ÁRVORE|" + _id + "|" + _name + "|" + age + "|" + _cleaningDifficulty + "|" + getType() + "|" + Cicle(_hotel.getCurrentSeason()); 
    }
}
