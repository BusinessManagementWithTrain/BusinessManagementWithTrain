package exceptions;

public class EqualsMaterialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public EqualsMaterialsException() {
		this("");
	}
	
	public EqualsMaterialsException(String error) {
		super(error);
	}
}
