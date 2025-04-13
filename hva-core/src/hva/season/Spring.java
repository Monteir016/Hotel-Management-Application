package hva.season;

public class Spring implements Season {
    public String getName() {
        return "primavera";
    }

    public Season advance() {
        return new Summer();
    }
    
}
