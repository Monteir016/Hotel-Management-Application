package hva.app.main;

import hva.HotelManager;
import hva.app.exceptions.FileOpenFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

class DoOpenFile extends Command<HotelManager> {
    DoOpenFile(HotelManager receiver) {
        super(Label.OPEN_FILE, receiver);
        addStringField("filename", Prompt.openFile());
    }

    @Override
    protected final void execute() throws CommandException {
        if (_receiver.getHotel().hasChanged()) {
            if (Form.confirm(Prompt.saveBeforeExit())) {
                new DoSaveFile(_receiver).execute();
            }
        }
        String filename = stringField("filename");
        try {
            _receiver.load(filename);
        } catch (hva.exceptions.FileOpenFailedException e) {
            throw new FileOpenFailedException(e);
        }
    }   
}

