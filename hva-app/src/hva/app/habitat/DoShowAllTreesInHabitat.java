package hva.app.habitat;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("Habitat ID", Prompt.habitatKey());
    }



    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("Habitat ID");
        try {
             _display.popup(_receiver.getAllTreesException(habitatId));
        } catch (hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(habitatId);
        }
    }

}
