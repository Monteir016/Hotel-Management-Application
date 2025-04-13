package hva.tree;

import hva.Hotel;

public class Evergreen extends Tree {
    
    public Evergreen(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }

    @Override
    public String getType() {
        return "PERENE";
    }

    @Override
    public String Cicle(String season) {
        return switch (season) {
            case "inverno" -> "LARGARFOLHAS";
            case "primavera" -> "GERARFOLHAS";
            case "verao" -> "COMFOLHAS";
            case "outono" -> "COMFOLHAS";
            default -> "";
        };
    }
}
