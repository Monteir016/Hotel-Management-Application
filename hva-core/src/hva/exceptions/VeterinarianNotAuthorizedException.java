package hva.exceptions;

public class VeterinarianNotAuthorizedException extends Exception {

    private static final String ERROR_MESSAGE = "O veterinário '";

    public VeterinarianNotAuthorizedException(String vetId, String speceiesId) {
        super(ERROR_MESSAGE + vetId + "' não pode ministrar vacinas à espécie '" + speceiesId + "'");
    }
    
}
