package hva.employee;

import java.util.List;
import hva.Hotel;

public class Caretaker extends Employee {
    public Caretaker(String id, String name, List<String> responsibilities, Hotel hotel) {
        super(id, name, responsibilities, hotel);
    }

    @Override
    public String getType() {
        return "TRT";
    }
}
