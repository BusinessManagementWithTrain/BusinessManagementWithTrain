package exceptions;

/**
 * Eccezione invocata qualora venisse creato un nome più
 * lungo di 12 caratteri
 */

public class MaximumCharactersException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public MaximumCharactersException() {
		this("");
	}
	
	public MaximumCharactersException(String error) {
		super(error);
	}
	

}
