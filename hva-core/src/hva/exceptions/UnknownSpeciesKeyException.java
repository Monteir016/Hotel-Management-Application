package hva.exceptions;

public class UnknownSpeciesKeyException extends Exception {

	private static final String ERROR_MESSAGE = "A espécie '";

	public UnknownSpeciesKeyException(String key) {
		super(ERROR_MESSAGE + key + "' não existe.");
	}
}
