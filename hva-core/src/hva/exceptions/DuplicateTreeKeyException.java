package hva.exceptions;

public class DuplicateTreeKeyException extends Exception {

	private static final String ERROR_MESSAGE = "A árvore '";

	public DuplicateTreeKeyException(String key) {
		super(ERROR_MESSAGE + key + "' já existe.");
	}

	public DuplicateTreeKeyException(String key, Exception cause){
		super(ERROR_MESSAGE + key + "' já existe.", cause);
	}
}

