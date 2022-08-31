package exceptions;

/**
 * Eccezione invocata qualora si provasse ad impostare un azienda
 * non associata ad un direttore come azienda mittente di una richiesta
 */

public class WrongSendingFactoryException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongSendingFactoryException() {
		this("");
	}
	
	public WrongSendingFactoryException(String errorMessage) {
		super(errorMessage);
	}
}
