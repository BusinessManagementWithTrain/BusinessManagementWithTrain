package exceptions;

public class WrongNeededQuantityException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongNeededQuantityException() {
		this("");
	}
	
	public WrongNeededQuantityException(String error) {
		super(error);
	}
}
