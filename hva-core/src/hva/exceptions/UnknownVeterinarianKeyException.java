package hva.exceptions;

public class UnknownVeterinarianKeyException extends Exception {

	private static final String ERROR_MESSAGE = "O veterinário '";

	public UnknownVeterinarianKeyException(String key) {
		super(ERROR_MESSAGE + key + "' não existe.");
	}
}
