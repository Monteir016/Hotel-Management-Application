package hva.app.habitat;

import hva.Hotel;
//import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("specieId", hva.app.animal.Prompt.speciesKey());
        addStringField("influence", Prompt.habitatInfluence());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String specieId = stringField("specieId");
        String influence = stringField("influence");
        try {
            _receiver.changeHabitatInfluence(habitatId, specieId, influence);
        } catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(habitatId);
        } catch (hva.exceptions.UnknownSpeciesKeyException e) {
            throw new UnknownSpeciesKeyException(specieId);
        }
    }

}
