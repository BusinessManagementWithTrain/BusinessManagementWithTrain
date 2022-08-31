package exceptions;

/**
 * Eccezione invocata qualora venga creata una nuova richiesta di scarico
 * senza aver prima soddisfatto la precedente
 */

public class AnotherEmptyRequestIsPresentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AnotherEmptyRequestIsPresentException() {
		this("");
	}
	
	public AnotherEmptyRequestIsPresentException(String errorMessage) {
		super(errorMessage);
	}
}
