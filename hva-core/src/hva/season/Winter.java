package hva.season;

public class Winter implements Season {
    public String getName() {
        return "inverno";
    }

    public Season advance() {
        return new Spring();
    }
}
