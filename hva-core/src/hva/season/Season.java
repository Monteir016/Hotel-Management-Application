package hva.season;

import java.io.Serializable;

public interface Season extends Serializable {
    String getName();
    Season advance();
}

