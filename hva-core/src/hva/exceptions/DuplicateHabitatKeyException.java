package hva.exceptions;

public class DuplicateHabitatKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O habitat '";

	public DuplicateHabitatKeyException(String key) {
		super(ERROR_MESSAGE + key + "' já existe.");
	}

	public DuplicateHabitatKeyException(String key, Exception cause){
		super(ERROR_MESSAGE + key + "' já existe.", cause);
	}
}
