package hva.app.main;

import java.io.IOException;

import hva.HotelManager;
import hva.app.exceptions.FileOpenFailedException;
import hva.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoNewFile extends Command<HotelManager> {
    DoNewFile(HotelManager receiver) {
        super(Label.NEW_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.getHotel().hasChanged()){
                if (Form.confirm(Prompt.saveBeforeExit())){
                    try{
                        _receiver.save();
                    } catch (MissingFileAssociationException e){
                        Form.requestString(Prompt.newSaveAs());             
                    }
                }
            }
                _receiver.reset();
                
            } catch (IOException e){
            throw new FileOpenFailedException(e);
        }
    }
}
