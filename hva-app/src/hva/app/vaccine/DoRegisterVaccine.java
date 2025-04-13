package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import java.util.List;
import java.util.Arrays;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("id", Prompt.vaccineKey());
        addStringField("name", Prompt.vaccineName());
        addStringField("species", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("id");
        String vaccineName = stringField("name");
        String speciesString = stringField("species");
        List<String> speciesList = Arrays.asList(speciesString.split(","));

        try {
            _receiver.registerVaccine(vaccineId, vaccineName, speciesList);
        } catch (hva.exceptions.DuplicateVaccineKeyException e) {
            throw new DuplicateVaccineKeyException(vaccineId);
        }
        catch (hva.exceptions.UnknownSpeciesKeyException e) {
            throw new UnknownSpeciesKeyException(speciesString);
        }
    }
}
