package hva;

import java.io.*;

import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.FileOpenFailedException;
import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateVaccineKeyException;
import hva.exceptions.DuplicateTreeKeyException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnrecognizedEntryException;



/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** The filename associated with the current hotel's state. */
    private String _filename = "";

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();

    /**
     * Gets the current hotel object
     *
     * @return the current Hotel instance
     */
    public Hotel getHotel() {
        return _hotel;
    }
    
    /**
     * Saves the serialized application's state into the file associated with the current hotel
     *
     * @throws FileNotFoundException if the file cannot be created or opened
     * @throws MissingFileAssociationException if the current hotel does not have an associated file
     * @throws IOException if there is an error while serializing the hotel's state to disk
     */
    public void save() throws  MissingFileAssociationException, IOException {
        if (_hotel.hasChanged() == false){ return; }
        if (_filename.equals("")){
            throw new MissingFileAssociationException();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))){
            oos.writeObject(_hotel);
            _hotel.setChanged(false);
        }
    }  

    /**
     * Saves the serialized application's state into a specified file
     *
     * @param filename the name of the file where the hotel's state will be saved
     * @throws FileNotFoundException if the file cannot be created or opened
     * @throws MissingFileAssociationException if the current hotel does not have an associated file
     * @throws IOException if there is an error while serializing the hotel's state to disk
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _filename = filename;
        save();
    }

   /**
     * Loads the serialized application's state from the specified file
     *
     * @param filename the name of the file containing the serialized application's state to load
     * @throws FileOpenFailedException if the file cannot be opened or there is an error while reading it
     */
    public void load(String filename) throws FileOpenFailedException {
        
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            _filename = filename;
            _hotel = (Hotel) ois.readObject();
            _hotel.setChanged(false);
        }
        catch (IOException | ClassNotFoundException e){
            throw new FileOpenFailedException(e);
        }
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException{
        try {
            this._hotel.importFile(filename);
        } catch (IOException | UnrecognizedEntryException | DuplicateAnimalKeyException | DuplicateEmployeeKeyException | DuplicateHabitatKeyException | DuplicateVaccineKeyException | DuplicateTreeKeyException | UnknownSpeciesKeyException | UnknownHabitatKeyException e){
            throw new ImportFileException(filename, e);
        }
    }

    /**
     * Resets the hotel manager
     */
    public void reset() {
        _hotel = new Hotel();
        _filename = "";
    }
}
