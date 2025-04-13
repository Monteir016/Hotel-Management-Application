package hva.app.habitat;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.UnknownHabitatKeyException;

class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
        addStringField("HabitatId", Prompt.habitatKey());
        addIntegerField("Area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("HabitatId");
        int area = integerField("Area");
        try {
            _receiver.changeHabitatArea(habitatId, area);
        } catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(habitatId);
        }
    }

}
