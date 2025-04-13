package hva.exceptions;

public class NoResponsibilityException extends Exception {

	private static final String ERROR_MESSAGE = "Responsabilidade (habitat ou espécie) '";

	public NoResponsibilityException(String employeeKey, String responsibilityKey) {
		super(ERROR_MESSAGE + responsibilityKey + "' não atribuída ao funcionário '" + employeeKey + "'.");
	}
}

