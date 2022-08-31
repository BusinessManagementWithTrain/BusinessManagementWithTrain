package exceptions;

public class AnotherEmptyRequestIsPresentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AnotherEmptyRequestIsPresentException() {
		this("");
	}
	
	public AnotherEmptyRequestIsPresentException(String errorMessage) {
		super(errorMessage);
	}
}
