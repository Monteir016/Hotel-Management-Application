package hva.exceptions;

public class DuplicateVaccineKeyException extends Exception {

	private static final String ERROR_MESSAGE = "A vacina '";

	public DuplicateVaccineKeyException(String key) {
		super(ERROR_MESSAGE + key + "' já existe.");
	}

	public DuplicateVaccineKeyException(String key, Exception cause){
		super(ERROR_MESSAGE + key + "' já existe.", cause);
	}
}
