package hva.season;

public class Summer implements Season {
    @Override
    public String getName() {
        return "verao";
    }

    @Override
    public Season advance() {
        return new Autumn();
    }
}
