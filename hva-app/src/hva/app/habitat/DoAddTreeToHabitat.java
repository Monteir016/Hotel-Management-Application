package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("Habitat ID", Prompt.habitatKey());
        addStringField("Tree ID", Prompt.treeKey());
        addStringField("name", Prompt.treeName());
        addIntegerField("age", Prompt.treeAge());
        addIntegerField("cleaning difficulty", Prompt.treeDifficulty());
        addStringField("type", Prompt.treeType());  
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("Habitat ID");
        String treeId = stringField("Tree ID");
        String treeName = stringField("name");
        int age = integerField("age");
        int cleaningDifficulty = integerField("cleaning difficulty");
        String type = stringField("type");

        try {
            String print = _receiver.addTreeToHabitat(habitatId, treeId, treeName, age, cleaningDifficulty, type);
            if (print != null)
                _display.popup(print);
        } catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(habitatId);
        } catch (hva.exceptions.DuplicateTreeKeyException e) {
            throw new DuplicateTreeKeyException(treeId);
        }
    }

}
