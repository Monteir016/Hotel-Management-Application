package hva.app.main;

import hva.HotelManager;

import hva.exceptions.MissingFileAssociationException;
//import hva.exceptions.UnavailableFileException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;


import java.io.IOException;
import java.io.FileNotFoundException;


class DoSaveFile extends Command<HotelManager> {
    DoSaveFile(HotelManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
    }

    @Override
    protected final void execute() {
        if (_receiver.getHotel().hasChanged() == false) {
            return;
        }
        try {
            _receiver.save();
        } catch (MissingFileAssociationException e) {
            try {
                _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
            } catch (MissingFileAssociationException | IOException ee) {
                ee.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
