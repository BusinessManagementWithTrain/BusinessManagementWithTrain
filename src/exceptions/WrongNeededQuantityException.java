package exceptions;

/**
 * Eccezione invocata qualora venisse creata una richiesta in cui la quantità
 * di materiale spedito supera la capienza di un magazzino
 */

public class WrongNeededQuantityException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongNeededQuantityException() {
		this("");
	}
	
	public WrongNeededQuantityException(String error) {
		super(error);
	}
}
