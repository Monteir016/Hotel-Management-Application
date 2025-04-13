package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.DuplicateEmployeeKeyException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("id", Prompt.employeeKey());
        addStringField("name", Prompt.employeeName());
        addStringField("type", Prompt.employeeType());
    }

    @Override
    protected void execute() throws CommandException {
        String id = stringField("id");
        String name = stringField("name");
        String type = stringField("type");

        try {
            if (type.equals("VET")) {
                _receiver.registerVet(id, name, null);
            } else if (type.equals("TRT")) {
                _receiver.registerCaretaker(id, name, null);
            }
        } catch (hva.exceptions.DuplicateEmployeeKeyException e) {
            throw new DuplicateEmployeeKeyException(id);
        }
    }
}
