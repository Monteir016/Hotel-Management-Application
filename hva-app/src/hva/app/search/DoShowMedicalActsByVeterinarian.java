package hva.app.search;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.UnknownVeterinarianKeyException;


class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
        addStringField("VetId", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String vetId = stringField("VetId");
        try {
            _display.popup(_receiver.showMedicalActsByVeterinarian(vetId));
        } catch (hva.exceptions.UnknownVeterinarianKeyException e) {
            throw new UnknownVeterinarianKeyException(vetId);
        }
    }

}
