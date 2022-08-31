package exceptions;

/**
 * Eccezione generata qualora si provasse a creare un oggetto
 * con un nome più corto di 1 carattere
 */

public class EmptyFieldException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EmptyFieldException() {
		this("");
	}
	
	public EmptyFieldException(String error) {
		super(error);
	}

}
