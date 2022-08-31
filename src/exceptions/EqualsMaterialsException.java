package exceptions;

/**
 * Eccezione invocata qualora si creasse un materiale uguale
 * nella sua forma grezza e in quella lavorata
 */

public class EqualsMaterialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public EqualsMaterialsException() {
		this("");
	}
	
	public EqualsMaterialsException(String error) {
		super(error);
	}
}
