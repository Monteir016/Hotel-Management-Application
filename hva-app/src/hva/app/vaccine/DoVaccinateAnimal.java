package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("VaccineId",Prompt.vaccineKey());
        addStringField("VetId",Prompt.veterinarianKey());
        addStringField("AnimalId",hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("VaccineId");
        String vetId = stringField("VetId");
        String animalId = stringField("AnimalId");
        try {
            boolean success = _receiver.vaccinateAnimal(vaccineId, animalId, vetId);
            if (!success) {
                _display.popup(Message.wrongVaccine(vaccineId, animalId));
            }
        } catch (hva.exceptions.UnknownVaccineKeyException e) {
            throw new UnknownVaccineKeyException(vaccineId);
        } catch (hva.exceptions.UnknownVeterinarianKeyException e) {
            throw new UnknownVeterinarianKeyException(vetId);
        } catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(animalId);
        } catch (hva.exceptions.VeterinarianNotAuthorizedException e){
            throw new VeterinarianNotAuthorizedException(vetId, _receiver.getAnimal(animalId).getId());
        }

    }

}
