package hva.exceptions;

public class FileOpenFailedException extends Exception {

    private static final String ERROR_MESSAGE = "Problema ao abrir ficheiro: ";

    public FileOpenFailedException(Exception cause) {
        super(ERROR_MESSAGE + cause.getMessage());
    }
    
}
