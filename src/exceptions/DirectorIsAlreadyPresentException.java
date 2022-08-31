package exceptions;

/**
 * Eccezione invocata qualora si provi ad assumere un
 * direttore già assunto
 */

public class DirectorIsAlreadyPresentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DirectorIsAlreadyPresentException() {
		this("");
	}
	
	public DirectorIsAlreadyPresentException(String errorMessage) {
		super(errorMessage);
	}

}
