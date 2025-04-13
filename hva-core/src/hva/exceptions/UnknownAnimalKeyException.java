package hva.exceptions;

public class UnknownAnimalKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O animal '";

	public UnknownAnimalKeyException(String key) {
		super(ERROR_MESSAGE + key + "' não existe.");
	}
}

