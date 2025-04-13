package hva.exceptions;

public class DuplicateAnimalKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O animal '";

	public DuplicateAnimalKeyException(String key) {
		super(ERROR_MESSAGE + key + "' já existe.");
	}

	public DuplicateAnimalKeyException(String key, Exception cause){
		super(ERROR_MESSAGE + key + "' já existe.", cause);
	}
}
