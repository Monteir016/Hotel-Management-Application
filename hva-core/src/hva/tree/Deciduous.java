package hva.tree;

import hva.Hotel;

public class Deciduous extends Tree {
    
    public Deciduous(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }
    
    @Override
    public String getType() {
        return "CADUCA";
    }

    @Override
    public String Cicle(String season) {
        return switch (season.toLowerCase()) {
            case "inverno" -> "SEMFOLHAS";
            case "primavera" -> "GERARFOLHAS";
            case "verao" -> "COMFOLHAS";
            case "outono" -> "LARGARFOLHAS";
            default -> "";
        };
    }
}
