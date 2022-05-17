package exceptions;

public class IsNotPresentException extends Exception {
	private static final long serialVersionUID = 1L;

	public IsNotPresentException() {
		this("");
	}
	
	public IsNotPresentException(String error) {
		super(error);
	}
}
