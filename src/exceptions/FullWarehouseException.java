package exceptions;

/**
 * Eccezione invocata qualora si provasse a caricare un magazzino
 * superando la sua capienza massima
 */

public class FullWarehouseException extends Exception {
	private static final long serialVersionUID = 1L;

	public FullWarehouseException() {
		this("");
	}
	
	public FullWarehouseException(String error) {
		super(error);
	}
}
