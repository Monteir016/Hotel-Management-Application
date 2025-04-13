package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.showVaccinations());
    }
}
