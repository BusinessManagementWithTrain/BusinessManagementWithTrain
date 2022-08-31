package exceptions;

/**
 * Eccezione invocata qualora si provasse a creare un magazzino la cui capienza
 * supera la capienza del treno
 */

public class WrongWarehouseCapacityException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongWarehouseCapacityException() {
		this("");
	}

	public WrongWarehouseCapacityException(String errorMessage) {
		super(errorMessage);
	}
}
