package hva.app.animal;


import hva.Hotel;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("id", Prompt.animalKey());
        addStringField("name", Prompt.animalName());
        addStringField("specieId", Prompt.speciesKey());
        addStringField("habitat", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("id");
        String animalName = stringField("name");
        String specieId = stringField("specieId");
        String habitat = stringField("habitat");

        if (!_receiver.getSpecie(specieId)) {
            String speciesName = Form.requestString(Prompt.speciesName());
            _receiver.registerSpecie(specieId, speciesName);
        }
        try {
            _receiver.registerAnimal(animalId, animalName, specieId, habitat);
        } catch (hva.exceptions.DuplicateAnimalKeyException e) {
            throw new DuplicateAnimalKeyException(animalId);
        } catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(habitat);
        }
    }
}
