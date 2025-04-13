package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;

class DoShowAllVaccines extends Command<Hotel> {

    DoShowAllVaccines(Hotel receiver) {
        super(Label.SHOW_ALL_VACCINES, receiver);
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.getAllVaccines());
    }
}
