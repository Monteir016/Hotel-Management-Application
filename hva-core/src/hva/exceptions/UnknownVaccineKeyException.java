package hva.exceptions;

public class UnknownVaccineKeyException extends Exception {

    private static final String ERROR_MESSAGE = "A vacina '";

    public UnknownVaccineKeyException(String key) {
        super(ERROR_MESSAGE + key + "' não existe.");
    }
    
}
