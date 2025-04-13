package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("id", Prompt.employeeKey());
        addStringField("responsibility", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String EmployeeId = stringField("id");
        String responsibility = stringField("responsibility");

        try {
            _receiver.addResponsibility(EmployeeId, responsibility);
        } catch (hva.exceptions.UnknownEmployeeKeyException e) {
            throw new UnknownEmployeeKeyException(EmployeeId);
        } catch (hva.exceptions.NoResponsibilityException e) {
            throw new NoResponsibilityException(EmployeeId, responsibility);
        }
    }

}
