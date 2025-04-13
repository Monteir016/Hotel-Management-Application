package hva.app.animal;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.UnknownAnimalKeyException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("AnimalId", Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("AnimalId");
        try {
        _display.popup(_receiver.CalculateSatisfactionOfAnimal(animalId));
        } catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(animalId);
        }   
    }
}
