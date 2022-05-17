package exceptions;

public class IsPresentException extends Exception {
	private static final long serialVersionUID = 1L;

	public IsPresentException() {
		this("");
	}
	
	public IsPresentException(String error) {
		super(error);
	}
}
