package hva.exceptions;

public class UnknownHabitatKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O habitat '";

	public UnknownHabitatKeyException(String key) {
		super(ERROR_MESSAGE + key + "' não existe.");
	}
}

