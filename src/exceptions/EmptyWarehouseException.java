package exceptions;

/**
 * Eccezione invocata qualora si provasse a scaricare
 * del materiale da un magazzino che non contiene abbastanza
 * materiale
 */

public class EmptyWarehouseException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyWarehouseException() {
		this("");
	}
	
	public EmptyWarehouseException(String error) {
		super(error);
	}
}
