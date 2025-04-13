package hva.exceptions;

public class UnknownEmployeeKeyException extends Exception {

    private static final String ERROR_MESSAGE = "O funcionário '";

    public UnknownEmployeeKeyException(String key) {
        super(ERROR_MESSAGE + key + "' não existe.");
    }
    
}
