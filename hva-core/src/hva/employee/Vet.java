package hva.employee;

import hva.Hotel;
import java.util.List;

public class Vet extends Employee {
    public Vet(String id, String name, List<String> responsibilities, Hotel hotel) {
        super(id, name, responsibilities, hotel); 
    }

    @Override
    public String getType() {
        return "VET";
    }
}
