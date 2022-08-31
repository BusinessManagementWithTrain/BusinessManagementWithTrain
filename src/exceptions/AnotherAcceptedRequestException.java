package exceptions;

/**
 * Eccezione invocata qualora fosse già stata accettata una richiesta
 * da un direttore e lo stesso provasse ad accettarne un'altra
 */

public class AnotherAcceptedRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public AnotherAcceptedRequestException() {
		this("");
	}
	
	public AnotherAcceptedRequestException(String error) {
		super(error);
	}
}
