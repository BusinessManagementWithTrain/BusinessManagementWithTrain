package exceptions;

public class DirectorIsAlreadyPresentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DirectorIsAlreadyPresentException() {
		this("");
	}
	
	public DirectorIsAlreadyPresentException(String errorMessage) {
		super(errorMessage);
	}

}
