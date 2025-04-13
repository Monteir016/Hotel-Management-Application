package hva.season;

public class Autumn implements Season {
    @Override
    public String getName() {
        return "outono";
    }

    @Override
    public Season advance() {
        return new Winter();
    }
}