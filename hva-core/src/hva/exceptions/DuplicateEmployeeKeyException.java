package hva.exceptions;

public class DuplicateEmployeeKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O funcionário '";

	public DuplicateEmployeeKeyException(String key) {
		super(ERROR_MESSAGE + key + "' já existe.");
	}

	public DuplicateEmployeeKeyException(String key, Exception cause){
		super(ERROR_MESSAGE + key + "' já existe.", cause);
	}
}
