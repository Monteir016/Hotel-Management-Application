package hva;

import java.io.Serial;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedHashMap;

import hva.animal.Animal;
import hva.habitat.Habitat;
import hva.habitat.Influence;
import hva.specie.Specie;
import hva.tree.Deciduous;
import hva.tree.Evergreen;
import hva.tree.Tree;
import hva.vaccine.Vaccine;
import hva.employee.Employee;
import hva.employee.Vet;
import hva.employee.Caretaker;
import hva.season.Season;
import hva.season.Spring;

import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateVaccineKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.DuplicateTreeKeyException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.NoResponsibilityException;
import hva.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownVaccineKeyException;
import hva.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.VeterinarianNotAuthorizedException;

/**
 * Hotel class that contains the main data structures for the hotel
 */
public class Hotel implements Serializable {

    private boolean _changed = false;

    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Map<String, Animal> _animals;
    private Map<String, Specie> _species;
    private Map<String, Habitat> _habitats;
    private Map<String, Vaccine> _vaccines;
    private Map<String, Employee> _employees;
    private Map<String, Tree> _trees;
    private Map<String, String> _vaccinations;
    private Map<String, String> _wrongVaccinations;
    private Season _currentSeason;
    private float _currentYear;
    
    /**
     * Constructor for the Hotel class that initializes Hashmaps for different classes
     */
    public Hotel() {
        _animals = new HashMap<>();
        _species = new HashMap<>();
        _habitats = new HashMap<>();
        _vaccines = new HashMap<>();
        _employees = new HashMap<>();
        _trees = new HashMap<>();
        _vaccinations = new LinkedHashMap<>();
        _wrongVaccinations = new LinkedHashMap<>();
        _currentSeason = new Spring();
        _currentYear = 0;
    }

    /**
     * Reads a text input file and creates domain entities
     *
     * @param filename the name of the text input file
     * @throws ImportFileException if an error occurs while reading the file
     * @throws UnknownHabitatKeyException if a habitat key is unknown
     * @throws DuplicateAnimalKeyException if an animal ID is duplicated
     * @throws DuplicateHabitatKeyException if a habitat ID is duplicated
     * @throws DuplicateEmployeeKeyException if an employee ID is duplicated
     * @throws DuplicateVaccineKeyException if a vaccine ID is duplicated
     * @throws DuplicateTreeKeyException if a tree ID is duplicated
     * @throws UnknownSpeciesKeyException if a species ID is unknown
     * @throws IOException if an input/output error occurs
     */
    void importFile(String filename) throws UnrecognizedEntryException, UnknownHabitatKeyException, DuplicateAnimalKeyException, DuplicateHabitatKeyException, DuplicateEmployeeKeyException, DuplicateVaccineKeyException, DuplicateTreeKeyException, UnknownSpeciesKeyException, IOException {
        String line;
        String[] fields;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                fields = line.split("\\|");
                int length = fields.length;
    
                switch (fields[0]) {
                    case "ANIMAL" -> {
                        if (length == 5) registerAnimal(fields[1], fields[2], fields[3], fields[4]);
                        else throw new UnrecognizedEntryException(fields[0]);
                    }
                    case "ESPÉCIE" -> {
                        if (length == 3) registerSpecie(fields[1], fields[2]);
                        else throw new UnrecognizedEntryException(fields[0]);
                    }
                    case "HABITAT" -> {
                        if (length == 4) {
                            registerHabitat(fields[1], fields[2], Integer.parseInt(fields[3]), null);
                        } else if (length == 5) {
                            registerHabitat(fields[1], fields[2], Integer.parseInt(fields[3]), List.of(fields[4].split(",")));
                        } else {
                            throw new UnrecognizedEntryException(fields[0]);
                        }
                    }
                    case "VACINA" -> {
                        if (length == 4) {
                            registerVaccine(fields[1], fields[2], List.of(fields[3].split(",")));
                        } else if (length == 3) {
                            registerVaccine(fields[1], fields[2], null);
                        } else {
                            throw new UnrecognizedEntryException(fields[0]);
                        }
                    }
                    case "TRATADOR" -> {
                        if (length == 3) {
                            registerCaretaker(fields[1], fields[2], null);
                        } else if (length == 4) {
                            registerCaretaker(fields[1], fields[2], List.of(fields[3].split(",")));
                        } else {
                            throw new UnrecognizedEntryException(fields[0]);
                        }
                    }
                    case "VETERINÁRIO" -> {
                        if (length == 3) {
                            registerVet(fields[1], fields[2], null);
                        } else if (length == 4) {
                            registerVet(fields[1], fields[2], List.of(fields[3].split(",")));
                        } else {
                            throw new UnrecognizedEntryException(fields[0]);
                        }
                    }
                    case "ÁRVORE" -> {
                        if (length == 6) {
                            if (fields[5].equals("PERENE"))
                                registerEvergreen(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                            else if (fields[5].equals("CADUCA"))
                                registerDeciduous(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                        } else throw new UnrecognizedEntryException(fields[0]);
                    }
                    default -> throw new UnrecognizedEntryException(fields[0]);
                }
            }
        }
    }

    /**
     * Registers a new animal in the hotel
     *
     * @param id the ID of the animal
     * @param name the name of the animal
     * @param specie the ID of the species the animal belongs to
     * @param habitat the ID of the habitat where the animal is located
     * @throws DuplicateAnimalKeyException if the animal ID is already registered
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public void registerAnimal(String id, String name, String specie, String habitat) throws DuplicateAnimalKeyException, UnknownHabitatKeyException {
        if (_animals.containsKey(id)) {
            throw new DuplicateAnimalKeyException(id);
        }
        Specie specieId = _species.get(specie);
        Habitat habitatId = _habitats.get(habitat);
        if (habitatId == null) {
            throw new UnknownHabitatKeyException(habitat);
        }
        Animal animal = new Animal(id, name, specieId, habitatId);
        habitatId.addAnimal(animal);
        _animals.put(animal.getId(), animal);
        setChanged(true);
    }
    
    /**
     * Transfers an animal to another habitat
     *
     * @param animalID the ID of the animal to be transferred
     * @param habitatID the ID of the habitat to transfer the animal to
     * @throws UnknownAnimalKeyException if the animal ID is not found
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public void transferToHabitat(String animalID, String habitatID) throws UnknownAnimalKeyException, UnknownHabitatKeyException {
        Animal animal = _animals.get(animalID);
        Habitat newhabitat = _habitats.get(habitatID);
        if (animal == null) {
            throw new UnknownAnimalKeyException(animalID);
        }
        if (newhabitat == null) {
            throw new UnknownHabitatKeyException(habitatID);
        }
        animal.getHabitat().removeAnimal(animal);
        newhabitat.addAnimal(animal);
        animal.setHabitat(newhabitat);
        setChanged(true);
    }

    /**
     * Calculates the satisfaction level of an animal
     *
     * @param animalID the ID of the animal
     * @return the calculated satisfaction level
     * @throws UnknownAnimalKeyException if the animal ID is not found
     */
    public int CalculateSatisfactionOfAnimal(String animalID) throws UnknownAnimalKeyException {
        Animal animal = _animals.get(animalID);
        if (animal == null) {
            throw new UnknownAnimalKeyException(animalID);
        }
        return animal.calculateSatisfaction();
    }

    /**
     * Register a new Specie entity
     *
     * @param id   ID of the species
     * @param name Name of the species
     */
    public void registerSpecie(String id, String name) {
        Specie specie = new Specie(id, name);
        _species.put(specie.getId(), specie);
        setChanged(true);
    }

    /**
     * Registers a new habitat in the hotel
     *
     * @param id the ID of the habitat
     * @param name the name of the habitat
     * @param area the area of the habitat
     * @param treeIds a list of Tree IDs associated with the habitat
     * @throws DuplicateHabitatKeyException if the habitat ID is already registered
     */
    public void registerHabitat(String id, String name, int area, List<String> treeIds) throws DuplicateHabitatKeyException {
        if (_habitats.containsKey(id)) {
            throw new DuplicateHabitatKeyException(id);
        }
        List<Tree> trees = (treeIds != null) ? 
            treeIds.stream().map(_trees::get).collect(Collectors.toList()) : new ArrayList<>();
        Habitat habitat = new Habitat(id, name, area, trees);
        _habitats.put(habitat.getId(), habitat);
        setChanged(true);
    }
    
    /**
     * Adds a new tree to a specified habitat
     *
     * @param habitatId the ID of the habitat
     * @param treeId the ID of the tree
     * @param treeName the name of the tree
     * @param age the age of the tree
     * @param cleaningDifficulty the cleaning difficulty of the tree
     * @param type the type of the tree ("PERENE" or "CADUCA")
     * @return the string representation of the added tree
     * @throws DuplicateTreeKeyException if the tree ID is already registered
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public String addTreeToHabitat(String habitatId, String treeId, String treeName, int age, int cleaningDifficulty, String type) throws DuplicateTreeKeyException, UnknownHabitatKeyException {
        Habitat habitat = _habitats.get(habitatId);
        if (habitat == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        if (_trees.containsKey(treeId)) {
            throw new DuplicateTreeKeyException(treeId);
        }
        Tree tree;
        if (type.equals("PERENE")) {
            registerEvergreen(treeId, treeName, age, cleaningDifficulty);
            tree = _trees.get(treeId);
        } else if (type.equals("CADUCA")) {
            registerDeciduous(treeId, treeName, age, cleaningDifficulty);
            tree = _trees.get(treeId);
        } else {
            return null;
        }
        habitat.addTree(tree);
        setChanged(true);
        return tree.toString();
    }

    /**
     * Registers a new vet in the hotel
     *
     * @param id the ID of the vet
     * @param name the name of the vet
     * @param SpecieIds a list of Specie IDs the vet is qualified to treat
     * @throws DuplicateEmployeeKeyException if the employee ID is already registered
     */
    public void registerVet(String id, String name, List<String> SpecieIds) throws DuplicateEmployeeKeyException {
        if (_employees.containsKey(id)) {
            throw new DuplicateEmployeeKeyException(id);
        }
        Vet vet = new Vet(id, name, SpecieIds,Hotel.this);
        _employees.put(vet.getId(), vet);
        setChanged(true);
    }

    /**
     * Registers a new caretaker in the hotel, class Employee
     *
     * @param id the ID of the caretaker
     * @param name the name of the caretaker
     * @param habitatIds a list of Habitat IDs the caretaker is responsible for
     * @throws DuplicateEmployeeKeyException if the employee ID is already registered
     */
    public void registerCaretaker(String id, String name, List<String> habitatIds) throws DuplicateEmployeeKeyException {
        if (_employees.containsKey(id)) {
            throw new DuplicateEmployeeKeyException(id);
        }
        Caretaker caretaker = new Caretaker(id, name, habitatIds,Hotel.this);
        _employees.put(caretaker.getId(), caretaker);
        setChanged(true);
    }

    /**
     * Adds a new responsibility to an employee
     *
     * @param id the ID of the employee
     * @param responsibility the ID of the responsibility (either a Specie or Habitat ID)
     * @throws UnknownEmployeeKeyException if the employee ID is not found
     * @throws NoResponsibilityException if the responsibility does not exist for the employee type
     */
    public void addResponsibility(String id, String responsibility) throws UnknownEmployeeKeyException, NoResponsibilityException {
        Employee employee = _employees.get(id);
        if (employee == null) {
            throw new UnknownEmployeeKeyException(id);
        }
        if (employee.getResponsibilities().contains(responsibility)) {
            return;
        }
        if (employee.getType().equals("VET") && !_species.containsKey(responsibility) ||
            employee.getType().equals("TRT") && !_habitats.containsKey(responsibility)) {
            throw new NoResponsibilityException(id,responsibility);
        }
        if ((employee.getType().equals("VET") && _species.containsKey(responsibility)) ||
            (employee.getType().equals("TRT") && _habitats.containsKey(responsibility))) {
            employee.addResponsibility(responsibility);
            setChanged(true);
        }
        
    }

    /**
     * Removes a responsibility from an employee
     *
     * @param id the ID of the employee
     * @param responsibility the ID of the responsibility to be removed
     * @throws NoResponsibilityException if the responsibility does not exist for the employee
     */
    public void removeResponsibility(String id, String responsibility) throws NoResponsibilityException {
        Employee employee = _employees.get(id);
        if (employee == null || !employee.getResponsibilities().contains(responsibility)) {
            throw new NoResponsibilityException(id,responsibility);
        }
        employee.removeResponsibility(responsibility);
        setChanged(true);
    }

    /**
     * Calculates the satisfaction level of an employee
     *
     * @param employeeID the ID of the employee
     * @return the calculated satisfaction level
     * @throws UnknownEmployeeKeyException if the employee ID is not found
     */
    public int CalculateSatisfactionOfEmployee(String employeeID) throws UnknownEmployeeKeyException {
        Employee employee = _employees.get(employeeID);
        if (employee == null) {
            throw new UnknownEmployeeKeyException(employeeID);
        }
        return employee.calculateSatisfaction();
    }

    /**
     * Register a new Vaccine entity
     *
     * @param id       ID of the vaccine
     * @param name     Name of the vaccine
     * @param specieId List of Specie IDs that the vaccine can be applied to
     */
    public void registerVaccine(String id, String name, List<String> specieIds) throws DuplicateVaccineKeyException, UnknownSpeciesKeyException {
        if (_vaccines.keySet().stream().anyMatch(vaccineId -> vaccineId.equalsIgnoreCase(id))) {
            throw new DuplicateVaccineKeyException(id);
        }
        if (specieIds != null) {
            for (String specieId : specieIds) {
                if (!_species.containsKey(specieId)) {
                    throw new UnknownSpeciesKeyException(specieId);
                }
            }
        }

        List<Specie> species = (specieIds != null) ? 
            specieIds.stream().map(_species::get).collect(Collectors.toList()) : new ArrayList<>();
        Vaccine vaccine = new Vaccine(id, name, species);
        _vaccines.put(vaccine.getId(), vaccine);
        setChanged(true);
    }
    
    /**
     * Vaccinates an animal with a specific vaccine administered by a veterinarian
     *
     * @param vaccineId the ID of the vaccine
     * @param animalId the ID of the animal to be vaccinated
     * @param vetId the ID of the veterinarian administering the vaccine
     * @return true if the vaccination was successful, false otherwise
     * @throws UnknownVaccineKeyException if the vaccine ID is not found
     * @throws UnknownAnimalKeyException if the animal ID is not found
     * @throws UnknownVeterinarianKeyException if the vet ID is not found or not a veterinarian
     * @throws VeterinarianNotAuthorizedException if the vet is not authorized to vaccinate the animal
     */
    public boolean vaccinateAnimal(String vaccineId, String animalId, String vetId) throws UnknownVaccineKeyException, UnknownAnimalKeyException, UnknownVeterinarianKeyException, VeterinarianNotAuthorizedException {
        Animal animal = _animals.get(animalId);
        if (animal == null) {
            throw new UnknownAnimalKeyException(animalId);
        }
        Employee vet = _employees.get(vetId);
        if (vet == null || !vet.getType().equals("VET")) {
            throw new UnknownVeterinarianKeyException(vetId);
        }
        Vaccine vaccine = _vaccines.get(vaccineId);
        if (vaccine == null) {
            throw new UnknownVaccineKeyException(vaccineId);
        }
        if (!vet.getResponsibilities().contains(animal.getSpecie().getId())) {
            throw new VeterinarianNotAuthorizedException(vetId, animalId);
        }
        boolean success = vaccine.addVaccinatedAnimal(animal, vetId);
        if (!success) {
            _wrongVaccinations.put(animalId + "|" + vaccineId, vetId);
        }
        animal.addVaccination(vaccine);
        _vaccinations.put(animalId + "|" + vaccineId, vetId);
        setChanged(true);
        return success;
    }
    
    /**
     * Returns a list of strings showing all vaccinations
     *
     * @return a list of vaccinations, or empty list if none
     */
    public List<String> showVaccinations() {
        if (_vaccinations.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> vaccinationToPrint = new ArrayList<>();
        for (String key : _vaccinations.keySet()) {
            String[] parts = key.split("\\|");
            String animalId = parts[0];
            String vaccineId = parts[1];
            String vetId = _vaccinations.get(key);
            Animal animal = _animals.get(animalId);
            String specieId = animal.getSpecie().getId();
            String record = "REGISTO-VACINA|" + vaccineId + "|" + vetId + "|" + specieId;
            vaccinationToPrint.add(record);
        }
        return vaccinationToPrint;
    }

    /**
     * Register a new Evergreen Tree entity that is part of the Tree's class
     *
     * @param id                 ID of the tree
     * @param name               Name of the tree
     * @param age                Age of the tree
     * @param cleaningDifficulty Cleaning difficulty level of the tree
     */
    public void registerEvergreen(String id, String name, int age, int cleaningDifficulty) {
        Evergreen evergreen = new Evergreen(id, name, age, cleaningDifficulty,Hotel.this);
        _trees.put(evergreen.getId(), evergreen);
        setChanged(true);
    }

    /**
     * Register a new Deciduous Tree entity that is part of the Tree's class
     *
     * @param id                 ID of the tree
     * @param name               Name of the tree
     * @param age                Age of the tree
     * @param cleaningDifficulty Cleaning difficulty level of the tree
     */
    public void registerDeciduous(String id, String name, int age, int cleaningDifficulty) {
        Deciduous deciduous = new Deciduous(id, name, age, cleaningDifficulty, Hotel.this);
        _trees.put(deciduous.getId(), deciduous);
        setChanged(true);
    }

    /**
     * Check if the hotel data has been changed
     *
     * @return true if data has been changed, false otherwise
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * Set the changed state of the hotel data
     *
     * @param changed changed state of the hotel data to true or false
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Get a list of all registered animals, sorted by AnimalId
     *
     * @return List of strings representing animals, or empty list if none
     */
    public List<String> getAllAnimals() {
        if (_animals.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> animalStrings = new ArrayList<>();
        Map<String, Animal> sortedAnimals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedAnimals.putAll(_animals);
        for (Animal animal : sortedAnimals.values()) {
            animalStrings.add(animal.toString());
        }
        return animalStrings;
    }

    /**
     * Get a list of all registered employees, sorted by EmployeeId
     *
     * @return List of strings representing employee, or empty list if none
     */
    public List<String> getAllEmployees() {
        if (_employees.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> employeeStrings = new ArrayList<>();
        Map<String, Employee> sortedEmployees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedEmployees.putAll(_employees);
        for (Employee employee : sortedEmployees.values()) {
            employeeStrings.add(employee.toString());
        }
        return employeeStrings;
    }

    /**
     * Get a list of all registered habitats, sorted by HabitatId
     *
     * @return List of strings representing habitats, or empty list if none
     */
    public List<String> getAllHabitats() {
        if (_habitats.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> habitatStrings = new ArrayList<>();
        Map<String, Habitat> sortedHabitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedHabitats.putAll(_habitats);
        for (Habitat habitat : sortedHabitats.values()) {
            habitatStrings.add(habitat.toString());
            List<String> trees = getAllTrees(habitat.getId());
            habitatStrings.addAll(trees);
        }
        return habitatStrings;
    }

    /**
     * Gets all trees from a specific habitat
     *
     * @param habitatId the ID of the habitat
     * @return list of strings representing trees in the habitat
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public List<String> getAllTreesException(String habitatId) throws UnknownHabitatKeyException{
        if (_habitats.get(habitatId) == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        return getAllTrees(habitatId);
    }

    /**
     * Gets a list of all trees in a specific habitat
     *
     * @param habitatId the ID of the habitat
     * @return list of strings representing trees in the habitat
     */
    public List<String> getAllTrees(String habitatId) {
        if (_trees.isEmpty()) {
            return Collections.emptyList();
        }
        Habitat habitat = _habitats.get(habitatId);
        List<String> treeStrings = new ArrayList<>();
        Map<String, Tree> sortedTrees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Tree tree : habitat.getTrees()) {
            sortedTrees.put(tree.getId(), tree);
        }
        for (Tree tree : sortedTrees.values()) {
            treeStrings.add(tree.toString());
        }
    
        return treeStrings;
    }
    
    /**
     * Changes the area of a specific habitat
     *
     * @param habitatId the ID of the habitat
     * @param area the new area of the habitat
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public void changeHabitatArea(String habitatId, int area) throws UnknownHabitatKeyException {
        Habitat habitat = _habitats.get(habitatId);
        if (habitat == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        habitat.changeArea(area);
        setChanged(true);
    }

    /**
     * Changes the influence of a habitat on a species
     *
     * @param habitatId the ID of the habitat
     * @param specieId the ID of the species
     * @param influence the new influence value (POS, NEG, NEU)
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     * @throws UnknownSpeciesKeyException if the species ID is not found
     */
    public void changeHabitatInfluence(String habitatId, String specieId, String influence) throws UnknownHabitatKeyException, UnknownSpeciesKeyException {
        Habitat habitat = _habitats.get(habitatId);
        if (habitat == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        Specie specie = _species.get(specieId);
        if (specie == null) {
            throw new UnknownSpeciesKeyException(specieId);
        }
        Influence influenceEnum = Influence.valueOf(influence);
        if (influenceEnum != null) {
            habitat.changeInfluence(specie, influenceEnum);
            setChanged(true);
        }
    }

    /**
     * Shows all animals in a specific habitat
     *
     * @param habitatId the ID of the habitat
     * @return list of strings representing animals in the habitat
     * @throws UnknownHabitatKeyException if the habitat ID is not found
     */
    public List<String> showAnimalsInHabitat(String habitatId) throws UnknownHabitatKeyException {
        Habitat habitat = _habitats.get(habitatId);
        if (habitat == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        List<Animal> animals = habitat.getAnimals();
        List<String> animalStrings = new ArrayList<>();
        Map<String, Animal> sortedAnimals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Animal animal : animals) {
            sortedAnimals.put(animal.getId(), animal);
        }
        for (Animal animal : sortedAnimals.values()) {
            animalStrings.add(animal.toString());
        }
        return animalStrings;
    }

    /**
     * Shows the medical acts performed by a specific veterinarian
     *
     * @param vetId the ID of the veterinarian
     * @return list of medical acts by the vet, or empty list if none
     * @throws UnknownVeterinarianKeyException if the vet ID is not found
     */
    public List<String> showMedicalActsByVeterinarian(String vetId) throws UnknownVeterinarianKeyException {
        Vet vet = (Vet) _employees.get(vetId);
        if (vet == null) {
            throw new UnknownVeterinarianKeyException(vetId);
        }
        List<String> medicalActs = new ArrayList<>();
        for (String key : _vaccinations.keySet()) {
            String[] parts = key.split("\\|");
            String animalId = parts[0];
            String vaccineId = parts[1];
            String vetIdVaccinations = _vaccinations.get(key);
            if (vetIdVaccinations.equals(vetId)) {
                Animal animal = _animals.get(animalId);
                String specieId = animal.getSpecie().getId();
                String print = "REGISTO-VACINA|" + vaccineId + "|" + vetId + "|" + specieId;
                medicalActs.add(print);
            }
        }
        return medicalActs;
    }
        
    /**
     * Shows the medical acts performed on a specific animal
     *
     * @param animalId the ID of the animal
     * @return list of medical acts on the animal, or empty list if none
     * @throws UnknownAnimalKeyException if the animal ID is not found
     */
    public List<String> showMedicalActsOnAnimal(String animalId) throws UnknownAnimalKeyException {
        Animal animal = _animals.get(animalId);
        if (animal == null) {
            throw new UnknownAnimalKeyException(animalId);
        }
        List<String> medicalActs = new ArrayList<>();
        for (String key : _vaccinations.keySet()) {
            String[] parts = key.split("\\|");
            String animalIdVaccinations = parts[0];
            String vaccineId = parts[1];
            String vetId = _vaccinations.get(key);
            if (animalIdVaccinations.equals(animalId)) {
                String specieId = animal.getSpecie().getId();
                String print = "REGISTO-VACINA|" + vaccineId + "|" + vetId + "|" + specieId;
                medicalActs.add(print);
            }
        }
        return medicalActs;
    }

    /**
     * Shows all wrong vaccinations, ordered
     *
     * @return list of wrong vaccinations, or empty list if none
     */
    public List<String> showWrongVaccinations() {
        if (_wrongVaccinations.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> wrongVaccinations = new ArrayList<>();
        for (String key : _wrongVaccinations.keySet()) {
            String[] parts = key.split("\\|");
            String animalId = parts[0];
            String vaccineId = parts[1];
            String vetId = _wrongVaccinations.get(key);
            Animal animal = _animals.get(animalId);
            String specieId = animal.getSpecie().getId();
            String print = "REGISTO-VACINA|" + vaccineId + "|" + vetId + "|" + specieId;
            wrongVaccinations.add(print);
        }
        return wrongVaccinations;
    }

    /**
     * Get a list of all registered vaccines, sorted by VaccineId
     *
     * @return List of strings representing vaccines, or empty list if none
     */
    public List<String> getAllVaccines() {
        if (_vaccines.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> vaccineStrings = new ArrayList<>();
        Map<String, Vaccine> sortedVaccines = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedVaccines.putAll(_vaccines);
        for (Vaccine vaccine : sortedVaccines.values()) {
            vaccineStrings.add(vaccine.toString());
        }
        return vaccineStrings;
    }

    /**
     * Gets the name of the current season in the hotel
     *
     * @return the name of the current season
     */
    public String getCurrentSeason() {
        return _currentSeason.getName();
    }

    /**
     * Gets the current year in the hotel as a float value
     *
     * @return the current year
     */
    public float getCurrentYearInt() {
        return _currentYear;
    }

    /**
     * Advances the season of the hotel by one step, updating the season and the year
     *
     * @return the code of the new season
     */
    public String advanceSeason() {
        _currentSeason = _currentSeason.advance();
        _currentYear += 0.25;
        return switch(_currentSeason.getName())
        {
            case "primavera" -> "0";
            case "verao" -> "1";
            case "outono" -> "2";
            case "inverno" -> "3";
            default -> "";
        };
    }

    /**
     * Calculates the global satisfaction level for all animals and employees in the hotel
     *
     * @return a string representing the rounded total satisfaction value
     */
    public String getGlobalSatisfaction() {
        double totalSatisfaction = 0;
        if (_animals != null) {
            for (Animal animal : _animals.values()) {
                totalSatisfaction += animal.calculateSatisfaction();
            }
        }
        if (_employees != null) {
            for (Employee employee : _employees.values()) {
                totalSatisfaction += employee.calculateSatisfaction();
            }
        }
        int print = (int) Math.round(totalSatisfaction);
        return String.valueOf(print);
    }

    /**
     * Gets the current year in the hotel as a float value
     *
     * @return the current year
     */
    public float getCurrentYear() {
        return _currentYear;
    }

    /**
     * Checks if a species exists in the hotel
     *
     * @param id the ID of the species
     * @return true if the species exists, false otherwise
     */
    public boolean getSpecie(String id) {
        if (_species.containsKey(id)) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves a habitat by its ID
     *
     * @param id the ID of the habitat
     * @return the Habitat object corresponding to the ID, or null if not found
     */
    public Habitat getHabitat(String id) {
        return _habitats.get(id);
    }

    /**
     * Retrieves an animal by its ID
     *
     * @param id the ID of the animal
     * @return the Animal object corresponding to the ID, or null if not found
     */
    public Animal getAnimal(String id) {
        return _animals.get(id);
    }


    /**
     * Counts the number of animals that belong to a specific species.
     *
     * @param specieId the ID of the species
     * @return the number of animals in that species
     */
    public int numberAnimalsBySpecie(String specieId) {
        int count = 0;
        for (Animal animal : _animals.values()) {
            if (animal.getSpecie().getId().equals(specieId)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves a list of all employees in the hotel.
     *
     * @return a list of Employee objects
     */
    public List<Employee> getEmployees() {
        return new ArrayList<>(_employees.values());
    }
}
