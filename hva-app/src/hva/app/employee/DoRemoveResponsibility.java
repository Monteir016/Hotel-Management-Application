package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.NoResponsibilityException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("id", Prompt.employeeKey());
        addStringField("responsibility", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String EmployeeId = stringField("id");
        String responsibility = stringField("responsibility");

        try {
            _receiver.removeResponsibility(EmployeeId, responsibility);
        } catch (hva.exceptions.NoResponsibilityException e) {
            throw new NoResponsibilityException(EmployeeId, responsibility);
        }
    }

}
